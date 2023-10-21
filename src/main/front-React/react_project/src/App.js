import {Route,Routes} from 'react-router-dom'
import './css/Index.css';

import Login from 'main/Login/Login_index';
import KakaoRedirect_react from 'main/oauth/KakaoRedirect-react';
import KakaoRedirect_java from 'main/oauth/KakaoRedirect-java';


function App() {
    return (
        <Routes>
            <Route path='/' element={<Login />} />
            {/* <Route path='/oauth/redirected/kakao' element={<KakaoRedirect_react />} /> */}
            <Route path='/oauth/redirected/kakao' element={<KakaoRedirect_java />} />
        </Routes>
      );
}

export default App;