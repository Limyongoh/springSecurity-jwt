import styled from "styled-components";
import {TextField, FormControl, IconButton,
  InputLabel,
  OutlinedInput,
  InputAdornment,
  Button
} from "@mui/material";
import{Visibility, VisibilityOff} from "@mui/icons-material";

// content 영역 style
const LoginInner_setyled = styled.div`
  text-align : center;
  width : 350px;
  height : 100%;
  border : 1px solid black;
  padding-bottom: 20px;
`;

const Logo_styled = styled.div`
  height:100px;
  padding-top : 20px;
`

// mui의 TextFild에 styled 적용
const TextFiled_setyled = styled(TextField)`
  width:300px;
`;

const Button_setyled = styled(Button)`
  width:300px;
  background-color: #0095f6;
`;

function Login() {
  return (
    <div id = "Login_content">
      <LoginInner_setyled>
        <Logo_styled>
          <h2> 뭐먹?</h2>
        </Logo_styled>

        <TextFiled_setyled 
          label="ID"
          variant="outlined"/>
          
          <FormControl sx={{ margin: 1, width: '300px' }} variant="outlined">
            <InputLabel >Password</InputLabel>
            <OutlinedInput
              type= 'text'//{showPassword ? 'text' : 'password'}
              endAdornment={
                <InputAdornment position="end">
                  <IconButton>
                    <Visibility />
                    {/* {showPassword ? <VisibilityOff /> : <Visibility />} */}
                  </IconButton>
                </InputAdornment>
              }
              label="Password"
            />
          </FormControl>

          <Button_setyled variant="contained">로그인</Button_setyled>



      </LoginInner_setyled>

    </div>
  );
}

export default Login;
