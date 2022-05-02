import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import axios from 'axios';

axios.defaults.baseURL = 'http://localhost:8090';
// axios.defaults.headers.common['Authorization'] =
// 	'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsZW9vZWwiLCJleHAiOjE2NTA1Nzg5ODUsImlhdCI6MTY1MDU3NzE4NX0.lMHsrjnVqACNvExLRhm_YlgvFe8Q2q8n6kaHCIBEVFo5eCQ73VS0itML5CX0XcTLS96OPL2RIA11p-dywDNmoQ';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
	<React.StrictMode>
		<BrowserRouter>
			<App />
		</BrowserRouter>
	</React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
