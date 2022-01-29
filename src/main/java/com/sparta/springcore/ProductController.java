package com.sparta.springcore;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor // final로 선언된 멤버 변수를 자동으로 생성합니다.
@RestController // JSON으로 데이터를 주고받음을 선언합니다.
public class ProductController {


    // 신규 상품 등록
    //클라이언트로 부터 상품정보를 전달받음
    @PostMapping("/api/products")
    public Product createProduct(@RequestBody ProductRequestDto requestDto) throws SQLException {

        //서비스 만들어주기. - 서비스에 어떤 정보를 넘겨줘야하나 - 클라이언트로부터 받은 requestDto를 넘겨줘야함.
        ProductService productService = new ProductService();
        //컨트롤러입장에서 product정보를 넘겨줘야하므로  Product product 앞에 적어줌.
        Product product = productService.createProduct(requestDto); // 서비스에 인풋.

// 이부분부터 서비스에 넘겨줌.
//// 요청받은 DTO 로 DB에 저장할 객체 만들기
//        Product product = new Product(requestDto);
//
//// DB 연결
//        Connection connection = DriverManager.getConnection("jdbc:h2:mem:springcoredb", "sa", "");
//
//// DB Query 작성 - DB언어로 ()부분 작성 /
////        maxid - 마지막아이디 불러오기 -> 마지막 다음id에 상품을 저장해야하므로.
////        execute.Query 로 명령을 내림.
//        PreparedStatement ps = connection.prepareStatement("select max(id) as id from product");
//        ResultSet rs = ps.executeQuery();
//        if (rs.next()) {
//// product id 설정 = product 테이블의 마지막 id + 1 으로 아이디 설정 , 아이디 못가져왓을 경우 에러메세지.
//            product.setId(rs.getLong("id") + 1);
//        } else {
//            throw new SQLException("product 테이블의 마지막 id 값을 찾아오지 못했습니다.");
//        }
////        product에 value를 insert. / select한 ps값에 insert 함.
//        ps = connection.prepareStatement("insert into product(id, title, image, link, lprice, myprice) values(?, ?, ?, ?, ?, ?)");
//        ps.setLong(1, product.getId());
//        ps.setString(2, product.getTitle());
//        ps.setString(3, product.getImage());
//        ps.setString(4, product.getLink());
//        ps.setInt(5, product.getLprice());
//        ps.setInt(6, product.getMyprice());
//
//// DB Query 실행
//        ps.executeUpdate();
//
//// DB 연결 해제
//        ps.close();
//        connection.close();

// 응답 보내기 - 클라이언트에게 응답보내기 .
        return product;
    }



    // 설정 가격 변경
    @PutMapping("/api/products/{id}")
    public Long updateProduct(@PathVariable Long id, @RequestBody ProductMypriceRequestDto requestDto) throws SQLException {

//        서비스 연결 -
        ProductService productService = new ProductService();
//        productService.updateProduct(id, requestDto);
        Product product =productService.updateProduct(id, requestDto);

////        여기부터 Service로 넘김--------------------------------------------------------------------
//        Product product = new Product();
//
////        최저가 0이상일 경우 - 에러발생.
////        if(requestDto.getMyprice() <= 0){
////            throw new RuntimeException("최저가를 0이상으로 입력해 주세요");
////        }
//// DB 연결
//        Connection connection = DriverManager.getConnection("jdbc:h2:mem:springcoredb", "sa", "");
//
//// DB Query 작성 -product 테이블에서 id=?인 값을 찾아라.
//        PreparedStatement ps = connection.prepareStatement("select * from product where id = ?");
//        ps.setLong(1, id);
//
//// DB Query 실행 - Db에 상품이 있는경우 / 없는경우 -nullpoint 에러처리.
//        ResultSet rs = ps.executeQuery();
//        if (rs.next()) {
//            product.setId(rs.getLong("id"));
//            product.setImage(rs.getString("image"));
//            product.setLink(rs.getString("link"));
//            //            테이블의 lprice-> lowprice로 변경 (columnlabel="lowprice")
//            product.setLprice(rs.getInt("lprice"));
//            product.setMyprice(rs.getInt("myprice"));
//            product.setTitle(rs.getString("title"));
//        } else {
//            throw new NullPointerException("해당 아이디가 존재하지 않습니다.");
//        }
//
//// DB Query 작성
//        PreparedStatement ps = connection.prepareStatement("update product set myprice = ? where id = ?");
//        ps.setInt(1, requestDto.getMyprice());
//        ps.setLong(2, product.getId());
//
//// DB Query 실행
//        ps.executeUpdate();
//
//// DB 연결 해제

//        ps.close();
//        connection.close();
//        ---------------------------------------------------------------------------------

// 응답 보내기 (업데이트된 상품 id)
        return product.getId();
    }

    // 등록된 전체 상품 목록 조회
    @GetMapping("/api/products")
    public List<Product> getProducts() throws SQLException {
       //list 정보를 클라이언트에게 보내줘야하므로 서비스에게 list정보를 받아야함.
        ProductService productService = new ProductService();
        //여기 products 값이 리턴됨 , getproducts클래스만들기
        List<Product> products= productService.getProducts();

//// 여기서부터 서비스로 넘김 ---------------------------------------------------------
//        List<Product> products = new ArrayList<>();
//
//// DB 연결
//        Connection connection = DriverManager.getConnection("jdbc:h2:mem:springcoredb", "sa", "");
//
//// DB Query 작성 및 실행 - 모든내용을 가져와라.
//        Statement stmt = connection.createStatement();
//        ResultSet rs = stmt.executeQuery("select * from product");
//
//// DB Query 결과를 상품 객체 리스트로 변환
//        while (rs.next()) {
//            Product product = new Product();
//            product.setId(rs.getLong("id"));
//            product.setImage(rs.getString("image"));
//            product.setLink(rs.getString("link"));
////            테이블의 lprice-> lowprice로 변경 (columnlabel="lowprice")
//            product.setLprice(rs.getInt("lprice"));
//            product.setMyprice(rs.getInt("myprice"));
//            product.setTitle(rs.getString("title"));
//            products.add(product);
//        }
//
//// DB 연결 해제
//        rs.close();
//        connection.close();
//        //-------------------------------------------------------------------------------

// 응답 보내기
        return products;
    }
}
