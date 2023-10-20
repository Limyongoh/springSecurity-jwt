import {Route,Routes} from 'react-router-dom'
import './css/Index.css';

import Login from 'main/Login/Login_index';
import KakaoCallBack from 'main/oauth/Kakaocallback';


function App() {
    return (
        <Routes>
            <Route path='/' element={<Login />} />
            <Route path='/auth/kakao/callback' element={<KakaoCallBack />} />
        </Routes>
      );
}

export default App;