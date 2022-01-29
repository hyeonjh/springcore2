package com.sparta.springcore;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    public void createProduct(Product product) throws SQLException {

        //db역할을 받음
        //여기부터 db역할
// DB 연결
        //getConnection의 exception 오류를 위로 보내줘 -> thorws SQLException -> Service에 createProduct(prodcut)로 이동
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:springcoredb", "sa", "");


// DB Query 작성 - DB언어로 ()부분 작성 /
//        maxid - 마지막아이디 불러오기 -> 마지막 다음id에 상품을 저장해야하므로.
//        execute.Query 로 명령을 내림.
        PreparedStatement ps = connection.prepareStatement("select max(id) as id from product");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
// product id 설정 = product 테이블의 마지막 id + 1 으로 아이디 설정 , 아이디 못가져왓을 경우 에러메세지.
            product.setId(rs.getLong("id") + 1);
        } else {
            throw new SQLException("product 테이블의 마지막 id 값을 찾아오지 못했습니다.");
        }
//        product에 value를 insert. / select한 ps값에 insert 함.
        ps = connection.prepareStatement("insert into product(id, title, image, link, lprice, myprice) values(?, ?, ?, ?, ?, ?)");
        ps.setLong(1, product.getId());
        ps.setString(2, product.getTitle());
        ps.setString(3, product.getImage());
        ps.setString(4, product.getLink());
        ps.setInt(5, product.getLprice());
        ps.setInt(6, product.getMyprice());

// DB Query 실행
        ps.executeUpdate();

// DB 연결 해제
        ps.close();
        connection.close();
    }

    public Product getProduct(Long id) throws SQLException {

//여기부터 repository에 넘김 - 서비스로부터 받음 - 여기부터 repository로 넘김 - 리포짓토리에서 받음 -------------
        Product product = new Product();
// DB 연결
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:springcoredb", "sa", "");

// DB Query 작성 -product 테이블에서 id=?인 값을 찾아라.
        PreparedStatement ps = connection.prepareStatement("select * from product where id = ?");
        ps.setLong(1, id);

// DB Query 실행 - Db에 상품이 있는경우 / 없는경우 -nullpoint 에러처리.
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            product.setId(rs.getLong("id"));
            product.setImage(rs.getString("image"));
            product.setLink(rs.getString("link"));
            //            테이블의 lprice-> lowprice로 변경 (columnlabel="lowprice")
            product.setLprice(rs.getInt("lprice"));
            product.setMyprice(rs.getInt("myprice"));
            product.setTitle(rs.getString("title"));
        }
//        이부분을 repository가 아닌 서비스가 처리함.
//        else {
//            throw new NullPointerException("해당 아이디가 존재하지 않습니다.");
//        }
//        ------------------------------------------------------------------------------


        // DB 연결 해제

        ps.close();
        connection.close();

        //가져온product의 값을 넘겨줌.
        return product;


    }

    public void updateMyprice(Long id, int myprice) throws SQLException {

        //DB연결하는 부분을 위에서 복붙,
        // DB 연결
        // getConnnecttion에 exception을 throws함
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:springcoredb", "sa", "");


        //여기부터 updateMyprice에 넘김 - 리포짓터리에서 받음.
// DB Query 작성
        PreparedStatement ps = connection.prepareStatement("update product set myprice = ? where id = ?");

//        ps.setInt(1, requestDto.getMyprice());
//        ps.setLong(2, product.getId());
        //
        ps.setInt(1, myprice);
        ps.setLong(2, id);

// DB Query 실행
        ps.executeUpdate();

// DB 연결 해제
        ps.close();
        connection.close();
        //---------------------------------------------------------------

    }

    public List<Product> getProducts() throws SQLException {

        // 여기서부터 서비스로 넘김 - 컨트롤러로 부터 받음 - 리포짓터리로 넘김 -- 서비스에서 받음.
        List<Product> products = new ArrayList<>();

// DB 연결
        // exception처리
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:springcoredb", "sa", "");

// DB Query 작성 및 실행 - 모든내용을 가져와라.
        Statement stmt = connection.createStatement();
        //전체를 가져올거임
        ResultSet rs = stmt.executeQuery("select * from product");

// DB Query 결과를 상품 객체 리스트로 변환
        // arraylist에 하나씩추가.
        while (rs.next()) {
            Product product = new Product();
            product.setId(rs.getLong("id"));
            product.setImage(rs.getString("image"));
            product.setLink(rs.getString("link"));
//            테이블의 lprice-> lowprice로 변경 (columnlabel="lowprice")
            product.setLprice(rs.getInt("lprice"));
            product.setMyprice(rs.getInt("myprice"));
            product.setTitle(rs.getString("title"));
            products.add(product);
        }

// DB 연결 해제
        rs.close();
        connection.close();
        //-------------------------------------------------------------------------------
//    서비스에 products를 넘김.
        return products;
    }
}
