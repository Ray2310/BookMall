package com.bookmall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.bookmall.domain.dto.BookDTO;
import com.bookmall.domain.entity.Book;
import com.bookmall.domain.entity.BookStandard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface BookMapper extends BaseMapper<Book> {

//    @Select("select * from book_standard where book_id = #{id}")
//    List<BookStandard> getStandardById(int id);
//
//    List<BookDTO> findFrontBooks();
//
//    @Update("update book set is_delete = 1 where id = #{id}")
//    void fakeDelete(Long id);
//
//    void insertBook(@Param("book") Book book);
//
//    @Select("SELECT discount * MIN(price) FROM book_standard gs, book WHERE book.id = gs.book_id AND book.id = #{id} ")
//    BigDecimal getMinPrice(Long id);
//
//    boolean saleBook(@Param("id")Long bookId,@Param("count") int count,@Param("money") BigDecimal totalPrice);
//
//
//    @Select("SELECT * FROM `book` WHERE is_delete = 0 ORDER BY sale_money DESC LIMIT 0,#{num}")
//    List<Book> getSaleRank(int num);
}
