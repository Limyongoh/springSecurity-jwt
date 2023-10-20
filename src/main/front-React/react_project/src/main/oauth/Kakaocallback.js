
import axios from 'axios'
import { useEffect } from 'react';


function KakaoCallBack() {
    
    useEffect(()=>{
        const params = new URL(document.location.toString()).searchParams;
        const code = params.get('code');
        const grant_type = "authorization_code";
        const client_id = process.env.REACT_APP_KAKAO_REST_API_KEY;
        const redirect_uri = process.env.REACT_APP_KAKAO_REDIRECT_URL;


        axios.post(`https://kauth.kakao.com/oauth/token?grant_type=${grant_type}&client_id=${client_id}&redirect_url=${redirect_uri}&code=${code}`,
        {},
        {
            headers:{
                "Content-type": "application/x-www-form-urlencoded;charset=utf-8"
            }
        }
        ).then((res)=>{
            const {data}  = res;
            const {access_token} = data;
            if(access_token){
                console.log(`Bearer ${access_token}`);
                axios.post("https://kapi.kakao.com/v2/user/me",
                    {},
                    {
                        headers : {
                            "Authorization": `Bearer ${access_token}`,
                            "Content-type": "application/x-www-form-urlencoded;charset=utf-8",
                        },
                    }
                ).then((res)=>{
                    console.log("사용자 정보가져오기 성공 : ");
                    console.log(res);
                })
            }else{
                console.log("access_token 없음");
            }
        });
    },[])
  
    return (
      <>
        callback
      </>
    );
  }
  
  export default KakaoCallBack;
  