import {Route,Routes} from 'react-router-dom'
import './css/Index.css';

import Main from 'main/Main_index';
import KakaoRedirect_react from 'main/oauth/KakaoRedirect-react';
import KakaoRedirect_java from 'main/oauth/KakaoRedirect-java';
import Content from 'main/content/Content';


function App() {
    return (
        <Routes>
            <Route path='/' element={<Main />} />
            {/* <Route path='/oauth/redirected/kakao' element={<KakaoRedirect_react />} /> */}
            <Route path='/oauth/redirected/kakao' element={<KakaoRedirect_java />} />
            <Route path='/content' element={<Content />}/>
        </Routes>
      );
}

export default App;