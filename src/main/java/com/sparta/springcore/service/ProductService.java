package com.sparta.springcore.service;

import com.sparta.springcore.model.Product;
import com.sparta.springcore.dto.ProductMypriceRequestDto;
import com.sparta.springcore.dto.ProductRequestDto;
import com.sparta.springcore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;




//스프링이 알아서 서비를 new해서 만들어서 스프링ioc컨테이너에 담아두고 빈으로 생성을해서 관리.
//Component : 1. 객체생성 (Service service = new Service();
//Component : 2. 스프링 IOC 컨테이너에 빈저장
//스프링 빈 이름 규칙 : 클래스의 앞글자만 소문자로 변경!
@Component
@Service
public class ProductService {

    private final ProductRepository productRepository;

    //생성자 만들기
    //ProductRepository productRepository 괄호안에 넣고 느슨한결합만들기. (repository의 final값을 불러옴)
    @Autowired
    public ProductService(ProductRepository productRepository){
//        ProductRepository productRepository = new ProductRepository();
         //final과 중복되므로 this를 써줌.

        this.productRepository = productRepository;
    }



    public Product createProduct(ProductRequestDto requestDto) throws SQLException {
//컨트롤러에서 서비스로 이동 --
        // 요청받은 DTO 로 DB에 저장할 객체 만들기 - 서비스의 역할.
        Product product = new Product(requestDto);

        //레파짓토리로 연결. - product를 넘겨줘야함 (title,image,link,lprice,myprice 정보를 넘겨줘야하므로)
//        ProductRepository productRepository = new ProductRepository(); 중복
        //레파짓토리에 인풋 - 세이브를 할거기떄문에 리턴값 받을거 없음,
        //repositoru에서 exception 처리를 서비스에서 이어받아 Public쪽으로 위로넘김. throws SQLException
//        productRepository.createProduct(product);
          productRepository.save(product);


        //서비스에서 컨트롤러로 product를 return으로 넘겨줌
        return product;

////여기부터 db역할
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


    }

    public Product updateProduct(Long id, ProductMypriceRequestDto requestDto) throws SQLException {

//        ProductRepository productRepository = new ProductRepository(); 중복
        //getProduct 를 리포짓토리에 생성.
        // getProduct의 exception을 throws함.
//        Product product = productRepository.getProduct(id);
        Product product = productRepository.findById(id)
        .orElseThrow(()-> new NullPointerException("해당아이디가 존재하지 않습니다. "));


//         if(product == null) {
//            throw new NullPointerException("해당 아이디가 존재하지 않습니다.");
//        }

         int myprice = requestDto.getMyprice();
         product.setMyprice(myprice);
//         productRepository.updateMyprice(id,myprice);
         productRepository.save(product);

        //updateMyprice 리포짓토리에 생성
//        productRepository.updateMyprice(id,requestDto.getMyprice());

        //리턴 정보 넘기기
        return product;


//        ---------------------------------- myprice의 값이 0이하값을 가질떄 오류 처리.-------------------------------
//        if(requestDto.getMyprice() <= 0){
//            throw new RuntimeException("희망 최저가는 0원 이상으로 설정해주세요 ");
//        }


////        여기부터 Service로 넘김 - controller로 받음 - 여기부터 repository로 넘김 ----------------------
//        Product product = new Product();
//
////        최저가 0이상일 경우 - 에러발생.
////        if(requestDto.getMyprice() <= 0){
////            throw new RuntimeException("최저가를 0이상으로 입력해 주세요");
////        }
//
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
//        }
////        이부분을 repository가 아닌 서비스가 처리함.
////        else {
////            throw new NullPointerException("해당 아이디가 존재하지 않습니다.");
////        }
////            여기까지 리포짓터리로 넘김. ------------------------------------------------------------------




//         //여기부터 updateMyprice에 넘김 -----------------------------------
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
//        //---------------------------------------------------------------


    }

    public List<Product> getProducts() throws SQLException {
//        ProductRepository productRepository = new ProductRepository(); 중복
        //exception처리
//        List<Product> products = productRepository.getProducts();
        List<Product> products = productRepository.findAll();

        //컨트롤러에게 리턴.
        return products;


//        // 여기서부터 서비스로 넘김 - 컨트롤러로 부터 받음 - 리포짓터리로 넘김
//        List<Product> products = new ArrayList<>();
//
//// DB 연결
//
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


    }
}
