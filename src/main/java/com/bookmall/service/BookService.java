package com.bookmall.service;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookmall.constants.Constants;
import com.bookmall.constants.RedisConstants;
import com.bookmall.domain.dto.BookDTO;
import com.bookmall.domain.entity.Book;
import com.bookmall.domain.entity.BookStandard;
import com.bookmall.exception.ServiceException;
import com.bookmall.mapper.BookMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.bookmall.commonUtils.PathUtils.getClassLoadRootPath;
import static com.bookmall.constants.Constants.UN_DELETE_BOOK;
import static com.bookmall.constants.Constants.UN_RECOMMEND_BOOK;
import static com.bookmall.constants.RedisConstants.BOOK_TOKEN_KEY;
import static com.bookmall.constants.RedisConstants.BOOK_TOKEN_TTL;

/**
 * 书籍请求逻辑层实现
 * @author Rayce
 */
@Service
public class BookService extends ServiceImpl<BookMapper, Book> {

    @Resource
    private RedisTemplate<String, Book> redisTemplate;
    @Resource
    private BookMapper bookMapper;
    /**
     * 获取前台的书籍
     * @return
     */
    public List<BookDTO> findFrontBooks() {
        return bookMapper.findFrontBooks();
    }
    /**
     *  查询一个书籍的信息
     * @param id
     * @return
     */
    public Book getBookById(Long id) {
        String redisKey = BOOK_TOKEN_KEY + id;
        //从redis中查，若有则返回
        ValueOperations<String, Book> valueOperations = redisTemplate.opsForValue();
        Book redisBook = valueOperations.get(redisKey);
        if(redisBook!=null){
            redisTemplate.expire(redisKey,BOOK_TOKEN_TTL, TimeUnit.MINUTES);
            return redisBook;
        }
        //若redis中没有则去数据库查
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Book::getIsDelete,false);
        queryWrapper.eq(Book::getId,id);
        Book dbBook = getOne(queryWrapper);
        if(dbBook!=null){
            //将书籍信息存入redis
            valueOperations.set(redisKey,dbBook);
            redisTemplate.expire(redisKey,BOOK_TOKEN_TTL, TimeUnit.MINUTES);
            return dbBook;
        }
        //数据库中没有则返回异常
        throw new ServiceException(Constants.NO_RESULT,"无结果");
    }

    public List<Book> getSaleRank(int num) {
        return bookMapper.getSaleRank(num);
    }

    /**
     * 查询书籍的规格
     * @param id
     * @return
     */
    public String getStandard(int id){
        List<BookStandard> standards = bookMapper.getStandardById(id);
        if(standards.size()==0){
            throw new ServiceException(Constants.NO_RESULT,"无结果");
        }
        return JSON.toJSONString(standards);
    }

    //------------------------------后台功能-------------------------------------

    /**
     *  保存书籍信息
     * @param book
     * @return
     */
    public Long saveOrUpdateBook(Book book) {
        System.out.println(book);
        if(book.getId()==null){
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            book.setCreateTime(df.format(LocalDateTime.now()));
            bookMapper.insertBook(book);
        }else{
            saveOrUpdate(book);
            redisTemplate.delete(BOOK_TOKEN_KEY + book.getId());
        }
        return book.getId();
    }

    public void update(Book book) {
        updateById(book);
        redisTemplate.delete(BOOK_TOKEN_KEY + book.getId());
    }

    /**
     * 假删除 通过设置删除字段为1
     * @param id
     */
    public void deleteBook(Long id) {
        redisTemplate.delete(BOOK_TOKEN_KEY+id);
        bookMapper.fakeDelete(id);
    }
    /**
     * 查询某书籍的最低规格价
     * @param id
     * @return
     */
    public BigDecimal getMinPrice(Long id){
        return bookMapper.getMinPrice(id);
    }

    /**
     *  设置图书的推荐字段
     * @param id
     * @param isRecommend
     * @return
     */
    public boolean setRecommend(Long id,Boolean isRecommend) {
        LambdaUpdateWrapper<Book> booksLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        booksLambdaUpdateWrapper.eq(Book::getId,id)
                .set(Book::getRecommend,isRecommend);
        return update(booksLambdaUpdateWrapper);
    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param searchText
     * @param categoryId
     * @return
     */
    public IPage<BookDTO> findPage(Integer pageNum, Integer pageSize, String searchText, Integer categoryId) {
        LambdaQueryWrapper<Book> query = Wrappers.<Book>lambdaQuery().orderByDesc(Book::getId);
        //对名称和描述进行模糊查询
        if (StrUtil.isNotBlank(searchText)) {
            query.like(Book::getName, searchText).or().like(Book::getDescription,searchText).or().eq(Book::getId,searchText);
        }
        if(categoryId != null){
            query.eq(Book::getCategoryId,categoryId);
        }
        //筛除掉已被删除的书籍
        query.eq(Book::getIsDelete,false);
        IPage<Book> page = this.page(new Page<>(pageNum, pageSize), query);
        //把book转为dto
        IPage<BookDTO> bookDTOPage = page.convert(book -> {
            BookDTO bookDTO = new BookDTO();
            BeanUtil.copyProperties(book, bookDTO);
            return bookDTO;
        });
        for (BookDTO book : bookDTOPage.getRecords()) {
            //附上最低价格
            book.setPrice(getMinPrice(book.getId()));
        }
        return bookDTOPage;
    }
    public IPage<Book> findFullPage(Integer pageNum, Integer pageSize, String searchText, Integer categoryId) {
        LambdaQueryWrapper<Book> query = Wrappers.<Book>lambdaQuery().orderByDesc(Book::getId);
        //对名称和描述进行模糊查询
        if (StrUtil.isNotBlank(searchText)) {
            query.like(Book::getName, searchText).or().like(Book::getDescription,searchText).or().eq(Book::getId,searchText);
        }
        if(categoryId != null){
            query.eq(Book::getCategoryId,categoryId);
        }
        //筛除掉已被删除的书籍
        query.eq(Book::getIsDelete,false);
        IPage<Book> page = this.page(new Page<>(pageNum, pageSize), query);
        for (Book book : page.getRecords()) {
            //附上最低价格
            book.setPrice(getMinPrice(book.getId()));
        }
        return page;
    }

    public List<BookDTO> listBook() {
        return bookMapper.findAllBooks();
    }
}
