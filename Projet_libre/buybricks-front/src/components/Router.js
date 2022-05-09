import { Routes, Route } from 'react-router-dom';
import { Home, Register, Profile, BrickDetails, BrickCatalog } from '../pages';

const Router = () => {
	return (
		<Routes>
			<Route path="/" element={<Home />} />
			<Route path="/register" element={<Register />} />
			<Route path="/profile" element={<Profile />} />
			<Route path="/brickDetails/:brickId" element={<BrickDetails />} />
			<Route path="/brickCatalog" element={<BrickCatalog />} />
			{/* <Route path="profil" element={<Profil />} />
			<Route path="Address" element={<Address />} />
			<Route path="Users" element={<Users />} /> */}
		</Routes>
	);
};

export default Router;
