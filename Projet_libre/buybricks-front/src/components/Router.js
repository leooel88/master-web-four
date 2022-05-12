import { Routes, Route, Navigate } from 'react-router-dom';
import {
	Home,
	Login,
	Register,
	Profile,
	BrickDetails,
	BrickCatalog,
	Basket,
	Orders,
	BrickEdition,
	NoRight,
} from '../pages';

import PrivateRoute from './PrivateRoute.js';

const Router = () => {
	return (
		<Routes>
			<Route path="/" element={<Home />} />
			<Route path="/noRight" element={<NoRight />} />
			<Route path="/login" element={<Login />} />
			<Route path="/register" element={<Register />} />
			<Route
				path="/profile"
				element={<PrivateRoute Component={Profile} />}
			/>
			<Route path="/brickDetails/:brickId" element={<BrickDetails />} />
			<Route path="/brickCatalog" element={<BrickCatalog />} />
			<Route
				path="/basket"
				element={<PrivateRoute Component={Basket} />}
			/>
			<Route
				path="/orders"
				element={<PrivateRoute Component={Orders} />}
			/>
			<Route
				path="/brickEdition"
				element={<PrivateRoute Component={BrickEdition} />}
			/>
			{/* <Route path="profil" element={<Profil />} />
			<Route path="Address" element={<Address />} />
			<Route path="Users" element={<Users />} /> */}
		</Routes>
	);
};

export default Router;
