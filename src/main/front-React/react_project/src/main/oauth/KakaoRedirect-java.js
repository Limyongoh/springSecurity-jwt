import React, {useEffect} from 'react';
import {useLocation, useNavigate} from 'react-router-dom';
import axios from 'axios';


function KakaoRedirect_java() {
  const location = useLocation();
  const navigate = useNavigate();

  const handleOAuthKakao = async (code) => {
      try {
          // 카카오로부터 받아온 code를 서버에 전달하여 카카오로 회원가입 & 로그인한다
          debugger;
          const response = await axios.get(`http://localhost:8080/oauth/login/kakao?code=${code}`);
          debugger;
          const data = response.data; // 응답 데이터
          console.log(data);
          alert("로그인 성공: " + data)
          navigate("/success");
      } catch (error) {
          navigate("/fail");
      }
  };

  useEffect(() => {
    debugger;
      const searchParams = new URLSearchParams(location.search); // Redirect의 url에서 params 정보 가져오기
      
      // 카카오는 Redirect 시키면서 code를 쿼리 스트링으로 준다.
      // url에서 params중 code 값 조회
      const code = searchParams.get('code');  
      if (code) {
          alert("CODE = " + code)
          handleOAuthKakao(code);
      }
  }, [location]);

  return (
      <div>
          <div>Processing...</div>
      </div>
  );
};
  
  export default KakaoRedirect_java;
  