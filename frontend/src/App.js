import './App.css';
import Home from './page/Home';
import Cardsets from './page/Cardsets';
import Cards from './page/Cards';
import { Routes, Route, Navigate } from 'react-router-dom';

function App() {
	return (
		<Routes>
			<Route path='/' element={<Home />} />
			<Route path='/:studentId' element={<Cardsets />} />
			<Route path='/:studentId/:cardsetId' element={<Cards />} />
			<Route path='*' element={<Navigate to='/' />} />
		</Routes>
	);
}

export default App;
