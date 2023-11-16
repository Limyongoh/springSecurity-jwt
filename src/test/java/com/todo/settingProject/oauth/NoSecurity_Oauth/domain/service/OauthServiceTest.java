package com.todo.settingProject.oauth.NoSecurity_Oauth.domain.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/*
- ☆☆☆☆☆ @Test 순서 상관 없이 test 소스를 작성 해줘야함 ☆☆☆☆☆

테스트 코드(TDD)의 종류
    - 통합 테스트 -> 세부적으로나누면 또 명칭이 존재 추후 추가예정
    - 단위 테스트
    - 인수 테스트 -> 추후 공부후 추가예정
    - 테스트 시 rollback 필요
        -> 통합테스트시 @Transactional 사용한 경우, spring에서 각 @Test 종료후 rollback해줌



통합테스트(@SprngBootTest)
    - spring container를 띄워 테스트 실행
    - 응용 프로그램의 여러 모듈을 결합하고 그룹으로 테스트하여 제대로 작동하는지 확인
    - @SprngBootTest 어노테이션 사용한 test는 통합테스트
        -> 단점
            - 속도 느림
            - 오류 찾기 어려움
        -> 장점
            - 운영환경과 가장 유사한 test 가능


단위테스트
    - 소스코드의 개별 단위를 테스트하는 방법
    - 프로그램의 각 부분을 테스트하고 개별 부분이 올바르게 동작하는지 확인
    - 실무에서의 테스트 코드 작성할 경우 단위테스트를 의미한다고 함
    -> 장점
        - 속도 빠름
        - 오류 찾기 쉬움
    -> 단점
        - 실제 환경에서는 제대로 동작하지 않을 수 있다고 함
        - 코드가 외부종속성과 올바르게 동작하는지 확인 x

@Transactional
    - @test 종료후 data rollback(실제 db에 반영x)


어느 블로그에서 정리된 내용
    - 통합 테스트 VS 단위 테스트
        -> DB, Spring container까지 같이 연동하여 테스트 진행 ==> 통합 테스트
        -> java로만 테스트를 실행하여 최소한의 단위로만 테스트 진행 ==> 단위 테스트
        => 즉, 단위 테스트를 통하여 모듈을 테스트한 후에 통합 테스트를 진행하기 때문에 단위 테스트가 훨씬 더 중요하고 좋은 테스트이다.


※ 테스트 코드가 짜기 어려운 경우 잘못짜여진 코드일 가능성이 높다......

*/
class OauthServiceTest {

    /*
    메소드 순서 상관 없이 test 소스를 작성 해줘야함

    테스트 결과 출력 방법
        -> Assertions.assertThat 사용 나중에 좀더 찾아볼것
        - example
            1)assertThat("Aaaaa").isEqualTo("Aaaaa"); -> true 출력
            2)assertThat("Aaaaa").isEqualTo("Aa"); -> false 출력
    */

    @Test
    @Transactional //-> 사용시 테스트 종료후 rollback
    void getAuthCodeRequestUrl() {
        assertThat("Aaaaa").isEqualTo("Aaaaa");
    }

    @Test
    void login() {
    }
}