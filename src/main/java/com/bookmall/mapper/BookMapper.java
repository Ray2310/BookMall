package com.bookmall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.bookmall.domain.dto.BookDTO;
import com.bookmall.domain.entity.Book;
import com.bookmall.domain.entity.BookStandard;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface BookMapper extends BaseMapper<Book> {

    @Select("select * from book_standard where book_id = #{id}")
    List<BookStandard> getStandardById(int id);

    @Select("SELECT book.*,MIN(book_standard.price)*discount as price FROM `book` LEFT JOIN book_standard on book.id = book_standard.book_id  WHERE is_delete = 0 AND recommend = 1 GROUP BY id  ORDER BY price ASC")
    List<BookDTO> findFrontBooks();

    @Select("SELECT book.*,MIN(book_standard.price)*discount as price FROM `book` LEFT JOIN book_standard on book.id = book_standard.book_id  WHERE is_delete = 0 GROUP BY id  ORDER BY price ASC")
    List<BookDTO> findAllBooks();

    @Update("update book set is_delete = 1 where id = #{id}")
    void fakeDelete(Long id);

    void insertBook(@Param("book") Book book);

    @Select("SELECT discount * MIN(price) FROM book_standard gs, book WHERE book.id = gs.book_id AND book.id = #{id} ")
    BigDecimal getMinPrice(Long id);

    boolean saleBook(@Param("id")Long bookId,@Param("count") int count,@Param("money") BigDecimal totalPrice);


    @Select("SELECT * FROM `book` WHERE is_delete = 0 ORDER BY sale_money DESC LIMIT 0,#{num}")
    List<Book> getSaleRank(int num);
}
