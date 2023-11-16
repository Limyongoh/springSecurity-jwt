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

import KakaLoginImage from 'image/kakao_login.png';
import TestImage from 'image/sample.PNG';

import {useState} from 'react';
import axios from 'axios'
import { useMediaQuery } from 'react-responsive';

import HorizontalLine from "css/HorizontalLine";


// 최상위 div 영역 styled 적용
const Inner_setyled = styled.div`
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
const Main_setyled = styled.div`
  text-align: center;
  width: 350px;
  height: 100%;
  border: 1px solid #c5c5c5;
  padding-top: 20px;
  padding-bottom: 20px;
  font-size: 14px;
`;

function Main() {
  const [mainType, setMainType] = new useState('login');
  const [showPassword, setShowPassword] = useState(false);
  const minSize = useMediaQuery({query: "(min-width: 840px)"});
  
  const [registInput, setRegistInput] = useState({
    email:'',
    phoneNumber:'',
    nickName: '',
    name: '',
    password:''
  });

  const [loginInput, setLoginInput] = useState({
    email:'',
    password:''
  });

  // password 보기 이벤트 핸들러
  const hadleClickShowPassword = () => {
    setShowPassword(!showPassword);
  }


  // api 호출 테스트 완료
  const  apiCall = async (e) =>{
    const response = await axios.get('/api/test/home');
    console.log(response);
  }

  const kakaLogin_java = async (e) =>{
    debugger;
    window.location.href = 'http://localhost:8080/oauth2/authorization/kakao'; 
  }

  const onChange = e => {
    const {value, name} = e.target;
    if(mainType == 'login'){
      setLoginInput(
        {...loginInput,
        [name]:value}
      );
      // console.log(loginInput);
    }else{
      setRegistInput(
        {...registInput,
        [name] : value}
        );
      // console.log(registInput);
    }

  }

  return (
    <>
      <div id="main_div">
        {minSize ?
          (<div id= "img_div">
            <img src={TestImage}></img>
          </div>
          ) : null
        }
        <div id = "main_content">
          <Inner_setyled>
            <Logo_styled>
              <h2> 뭐먹?</h2>
            </Logo_styled>

            {mainType == 'login'?
              (
              <div>
                <TextField
                  style={{width :"300px"}}
                  label="이메일 주소"
                  variant="outlined"
                  size="small"
                  name="email"
                  onChange={onChange}/>
                  
                <FormControl sx={{ margin: 1, width: '300px' }} variant="outlined">
                  <InputLabel size="small">비밀번호</InputLabel>
                  <OutlinedInput
                    type= {showPassword ? 'text': 'password'}
                    size="small"
                    endAdornment={
                      <InputAdornment position="end">
                        <IconButton
                          onClick={hadleClickShowPassword}
                        >
                          {showPassword ? <Visibility/> : <VisibilityOff/>}
                        </IconButton>
                      </InputAdornment>
                    }
                    label="Password"
                    name="password"
                    onChange={onChange}
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
                  <img 
                    src={KakaLoginImage} 
                    onClick={(e)=> {
                        // kakaLogin();
                        kakaLogin_java();
                      }
                    }>
                  </img>
                </KakaoLogin_style>

                <PasswordSearch_styled onClick={(e)=>{alert("비밀번호찾기")}}>
                  비밀번호를 잊으셨나요?
                </PasswordSearch_styled>
              </div>
              )
              :
              (
              <div>
                  <div id="regist_title">가입해서 시스템을 사용하세요</div>
                  <KakaoLogin_style>
                    <img 
                      src={KakaLoginImage} 
                      onClick={(e)=> {
                          // kakaLogin();
                          kakaLogin_java();
                        }
                      }>
                    </img>
                  </KakaoLogin_style>
                  <HorizontalLine text='또는'/>
                  <TextField
                    style={{width :"300px"}}
                    label="이메일 주소"
                    variant="outlined"
                    size="small"
                    name="email"
                    onChange={onChange}/>

                  <FormControl sx={{ margin: 1, width: '300px' }} variant="outlined">
                    <InputLabel size="small">비밀번호</InputLabel>
                    <OutlinedInput
                      type= {showPassword ? 'text' : 'password'}
                      size="small"
                      endAdornment={
                        <InputAdornment position="end">
                          <IconButton
                            onClick={hadleClickShowPassword}
                          >
                            {showPassword ? <Visibility/> : <VisibilityOff/>}
                          </IconButton>
                        </InputAdornment>
                      }
                      label="Password"
                      name="password"
                      onChange={onChange}
                    />
                  </FormControl>

                  {/* <TextField
                    style={{width :"300px"}}
                    label="핸드폰 번호"
                    variant="outlined"
                    size="small"
                    name="phoneNumber"
                    onChange={onChange}/> */}

                  <TextField
                    style={{width :"300px"}}
                    label="닉네임"
                    variant="outlined"
                    size="small"
                    name="nickName"
                    onChange={onChange}/>

                  <TextField
                    style={{width :"300px", marginTop:'8px'}}
                    label="사용자 이름"
                    variant="outlined"
                    size="small"
                    name="name"
                    onChange={onChange}/>

                  <Button 
                    style={{width:"300px", marginTop:'8px'}} 
                    variant="contained"
                    onClick={(e)=>{alert("가입")}}>
                      가입
                  </Button>
              </div>
              ) 
            }
          </Inner_setyled>
          <div id="main_registe">
            {mainType == 'login'?
              (
              <Main_setyled>
                계정이 없으신가요?
                <span 
                  onClick={(e)=>{setMainType('regist')}}
                  style={{color:"#309cf7", cursor:"pointer"}}
                >
                  &nbsp;가입하기
                </span>
              </Main_setyled>
              )
              :
              (
                <Main_setyled>
                  계정이 있으신가요?
                  <span 
                    onClick={(e)=>{setMainType('login')}}
                    style={{color:"#309cf7", cursor:"pointer"}}
                  >
                    &nbsp;로그인
                  </span>
                </Main_setyled>
              )
            }
          </div>
        </div>
      </div>
      
    </>
  );
}

export default Main;
