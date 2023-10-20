import styled from "styled-components";
import{Visibility, VisibilityOff} from "@mui/icons-material";
import {TextField, 
        FormControl, 
        IconButton,
        InputLabel,
        OutlinedInput,
        InputAdornment,
        Button,
  } from "@mui/material";

import kakaLogin from 'image/kakao_login.png';

import {useState} from 'react';
import axios from 'axios'

import HorizontalLine from "css/HorizontalLine";


// 최상위 div 영역 styled 적용
const LoginInner_setyled = styled.div`
  text-align: center;
  width: 350px;
  height: 100%;
  border: 1px solid #c5c5c5;
  padding-bottom: 20px;
`;

// logo div 영역 styled 적용
const Logo_styled = styled.div`
  height: 100px;
  padding-top: 20px;
`;

//카카오 로그인 div 영역 styled 적용
const KakaoLogin_style = styled.div`
  cursor: pointer;
  margin-bottom: 10px;
  width: 100%;
`;
  
// password 찾기 span 영역 styled 적용
const PasswordSearch_styled = styled.span`
  font-size: 12px;
  color: #0c4072;
  cursor: pointer;
`

// 회원가입 div 영역 styled 적용
const Registe_setyled = styled.div`
  text-align: center;
  width: 350px;
  height: 100%;
  border: 1px solid #c5c5c5;
  padding-top: 20px;
  padding-bottom: 20px;
  font-size: 14px;
`;

function Login() {
  const [showPassword, setShowPassword] = useState(false);

  // password 보기 이벤트 핸들러
  const hadleClickShowPassword = () => {
    setShowPassword(!showPassword);
  }


  // api 호출 테스트 완료
  const  apiCall = async (e) =>{
    const response = await axios.get('/test/home');
    console.log(response);
  }

  return (
    <>
      <div id = "Login_content">
        <LoginInner_setyled>
          <Logo_styled>
            <h2> 뭐먹?</h2>
          </Logo_styled>

          <TextField
          style={{width :"300px"}}
            label="ID"
            variant="outlined"
            size="small"/>
            
            <FormControl sx={{ margin: 1, width: '300px' }} variant="outlined">
              <InputLabel size="small">Password</InputLabel>
              <OutlinedInput
                type= {showPassword ? 'text' : 'password'}
                size="small"
                endAdornment={
                  <InputAdornment position="end">
                    <IconButton
                      onClick={hadleClickShowPassword}
                    >
                      {showPassword ? <VisibilityOff /> : <Visibility />}
                    </IconButton>
                  </InputAdornment>
                }
                label="Password"
              />
            </FormControl>

            <Button 
              style={{width:"300px"}} 
              variant="contained"
              onClick={(e)=>{alert("로그인")}}>
                로그인
            </Button>

            <HorizontalLine text='또는'/>
            
            <KakaoLogin_style>
              <span onClick={(e)=>{alert("카카오 ㄱ로그인")}}>
                카카오 로그인
              </span>
              <img src={kakaLogin}></img>
            </KakaoLogin_style>

            <PasswordSearch_styled onClick={(e)=>{alert("비밀번호찾기")}}>
              비밀번호를 잊으셨나요?
            </PasswordSearch_styled>
            
            {/* <Button 
              style={{width:"300px"}} 
              variant="contained"
              onClick={(e)=>{apiCall(e)}}>
                api 호출 테스트
            </Button> */}
        </LoginInner_setyled>

      </div>

      <div id="Login_registe">
        <Registe_setyled>
          계정이 없으신가요?
          <span 
            onClick={(e)=>{alert("회원가입 하슈")}}
            style={{color:"#309cf7", cursor:"pointer"}}
          >
            &nbsp;가입하기
          </span>
        </Registe_setyled>
      </div>
    </>
  );
}

export default Login;
