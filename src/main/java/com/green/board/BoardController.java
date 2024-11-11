package com.green.board;

import com.green.board.model.BoardInsReq;
import com.green.board.model.BoardSelOneRes;
import com.green.board.model.BoardSelRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    Controller의 역할 : 요청(request)을 받고 응답(response) 을 처리하는 객체
                       로직처리는 하지 않는다. ( 로직 담당은 서비스 )

    @ = Annotation(애노테이션)
    @Controller - 응답을 html ( 데이터로 만든 화면을 return )
    @RestController - 응답을 json ( 데이터만 응답 )

    @RequestMapping - URL과 클래스 아래에 있는 Method 맵핑(연결)
                      class에 RequestMapping 전체 메소드 주소가 맵핑

    @PostMapping - URL + Post 방식으로 요청이 왔을 시 담당자

    요청과 응답은 (header, body)로 이루어져있음
    header에는 목적지(URL), 방식, 인코딩 정보 등등
    body에는 값, 데이터가 담겨져 있음

    브라우저를 통해 요청을 보낼 때 URL과 method를 함께 보낸다.
    브라우저의 주소창에 주소값을 적고 엔터는 (URL + GET방식 + 데이터)를 보내는 방식(Key/Value)으로 요청을 보낸다.
    get방식과 post방식의 큰 차이점은 데이터를 보낼 때 보여지냐 안 보여지냐 차이가 있는데
    1. 쿼리스트링 방식 ( 파라미터라고 부르기도 함 ) : header(URL)에 데이터를 포함하는 방식
    2. body에 담아서 보내는 방식

    쿼리스트링 모양 : URL + 쿼리스트링 (?로 시작 key=value, 여러개라면 & 구분)
                   www.naver.com?name=홍길동&age=12&height=172.1

    대용량의 데이터를 보내야할 때도 body에 데이터를 담아서 보낸다. URL은 길이제한이 있기 때문에
    URL에 데이터를 포함하는 쿼리스트링은 대용량을 보낼 수 없다.

    JSON(JavaScript Object Notation) : 자바스크립트에서 객체를 만들 때 사용하는 문법을 이용하여
                                       데이터를 표현하는 포맷(형식), Key와 Value로 이루어져 있음.
    예를들어 name은 홍길동, age는 22살, height는 178.2 데이터를 JSON으로 표현하면
    "{
        "name" : "홍길동",
        "age" : 22,
        "height" : 178.2
    }"
    이렇게 표현하는 문자열이다.
    {}는 객체를 의미하고 [] 배열을 의미한다. ""문자열, 숫자형은 ""없이 표현한다.
    Key는 무조건 "" 감싸줘야 한다.

    Restful 이전에는 get, post방식 밖에 없었음.
    get방식은 주로 쿼리스트링 방식을 사용 - 데이터를 읽어올 때 (간혹 삭제때도 사용함)
    post방식은 body에 데이터를 담아서 보내는 방식으로 사용 - 데이터를 저장/수정/삭제 할 때 사용함.
    일단 get방식이 처리 속도가 빠름. 데이터 처리가 아닌 단순 화면을 띄울 때도 get방식을 사용함.

    예를들어 로그인을 하는 상황에서 로그인을 하는 화면이 띄워져야 한다.
    작업(1) : 로그인 하는 화면은 get방식으로 URL은 / login을 요청하면 로그인하는 화면이 화면에 나타났다.
             이하 (get) / login 이렇게 표현.
    작업(2) : 그 다음, 아이디/비번을 작성하고 로그인 버튼을 누르면
             (post) / login, 아이디/비번은 body에 담아서 요청을 보냈다.

    URL은 같은데 method(get/post)로 작업을 구분했다. (마치 if문처럼)

    위 작업은 2가지밖에 없었기 때문에 같은 주소값으로 method를 구분할 수 있다.
    그런데 CRUD(작업이 4가지)를 해야되는 상황에서는 작업 구분을 주소값으로 해야한다.

    (get) / board - 게시판 리스트 보기 화면
    (get) / board_detail - 게시판 글 하나 보기 화면
    (get) / board_create - 게시판 글 등록하는 화면
    (post) / board_create - 게시판 글 등록하는 작업 처리
    (get) / board_mod - 게시판 글 수정하는 화면
    (post) / board_modify - 게시판 글 수정하는 작업 처리
    (get/post) / board_delete - 게시판 글 삭제하는 작업 처리

    첫 페이지(index화면)를 띄울 때 소프트웨어(프론트엔드 작업 코드)가 모두 다운로드 됨.
    화면 이동은 모두 FE코드가 작동하는 것. 화면 만들기는 Client 리소스를 사용하여 그린다.
    (Rendering) 화면마다 데이터가 필요하면 BE(백엔드)에게 요청을 한다.
    누가? FE작업코드가 요청을 보낸다.
    그래서 BE는 이제 화면은 신경쓰지 않아도 된다. FE 코드가 요청한 작업에 응답만 해주면 된다.

    Client 리소스 : Client, 즉 요청을 보낸 컴퓨터의 자원을 사용한다. (CPU, RAM 등)

    Restful방식은 화면은 없고 작업만 신경쓰면 된다.
    ( 요청의 method는 크게 4가지로 나뉘어진다. )
    - POST 방식 : Create - Insert 작업
    - GET 방식
    - PUT / PATCH 방식
    - DELETE 방식

    POST방식과 PUT/PATCH 방식은 주로 데이터를 body에 담아서 보내고
    GET, DELETE방식은 Query String or Path Variable을 사용해서 데이터를 보낸다.
    FE가 BE한테 (URL + method + 데이터 요청(Request))을 하고 BE는 JSON으로 응답(Response)

    (post) / board - 글 등록
    (get) / board?page=1 - 리스트 데이터(row가 여러개) //위와는 메소드로만 구분을 했다.
    (get) / board/ - 끝에 /만 붙여도 위 URL과 다른 요청이 된다. (Tip)
    (get) / board?aaa=2 - /board?page=1과 같은 요청이다. URL이 같으면 같은 요청
    (get) / board/1 - 튜플 1개 데이터(row가 1줄)을 보고 싶을 때, 1은 pk, Path Variable이라고 부름
    (put / patch) / board - 글 수정
    (delete) / board - 글 삭제(Path Variable or Query String으로 pk값 전달)
 */

/*
    final 붙은 멤버필드 DI(Dependency Injection :의존성,참조 주입)를 받을 수 있게 생성자를 만든다.
    애노테이션 생략하면 오버로딩된 생성자를 직접 만들어주어야 한다.

 */
@RequiredArgsConstructor
@RestController // 빈 등록 + 컨트롤러 임명, 빈등록은 스프링 컨테이너가 직접 객체화를 한다.
@RequestMapping("/board")
public class BoardController {
    private final BoardService service; //final을 붙이면 생성자를 반드시 만들어줘야한다.

    /*
    @RequiredArgsConstructor 애노테이션을 붙이면 아래 생성자가 자동으로 만들어진다.
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }
    */

    //insert(Create)
    @PostMapping // (post) /board 요청이 오면 이 메소드가 응답 담당자
    //@PostMapping("/board") : @RequestMapping("/board") 이 코드가 없었다면 URL을 작성해줘야 한다.
    //@RequestBody는 요청이 올때 데이터가 JSON형태로 오니까 거기에 맞춰서 데이터를 받자.
    public int insBoard(@RequestBody BoardInsReq p) {
        System.out.println(p);
        return service.insBoard(p);
    }

    // (객체 >> JSON) 바꾸는 직렬화 작업 자동으로 해준다.
    // localhost:8080/board
    @GetMapping
    public List<BoardSelRes> selBoardList() {
        return service.selResList();
    }

    @GetMapping("{boardId}")
    public BoardSelOneRes selBoardOne(@PathVariable int boardId) {
        return service.selBoardOne(boardId);
    }
}
