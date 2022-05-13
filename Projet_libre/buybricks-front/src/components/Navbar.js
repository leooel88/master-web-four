import { Link } from 'react-router-dom';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const Navbar = () => {
	const navigate = useNavigate();

	const profileLogin = () => {
		if (
			localStorage.getItem('userId') &&
			localStorage.getItem('authToken')
		) {
			return (
				<Link
					to="/profile"
					class="block py-2 pr-4 pl-3 text-gray-700 border-b border-gray-100 hover:bg-gray-50 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-gray-400 md:dark:hover:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700"
				>
					Profile
				</Link>
			);
		} else {
			return (
				<Link
					to="/login"
					class="block py-2 pr-4 pl-3 text-gray-700 border-b border-gray-100 hover:bg-gray-50 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-gray-400 md:dark:hover:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700"
				>
					Login
				</Link>
			);
		}
	};

	const registerLogin = () => {
		if (
			localStorage.getItem('userId') &&
			localStorage.getItem('authToken')
		) {
			return;
		} else {
			return (
				<li>
					<Link
						to="/register"
						class="block py-2 pr-4 pl-3 text-gray-700 border-b border-gray-100 hover:bg-gray-50 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-gray-400 md:dark:hover:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700"
					>
						Register
					</Link>
				</li>
			);
		}
	};

	const handleAdminRoute = async (event) => {
		let result = '';
		try {
			result = await axios.get('/user/isadmin', {
				headers: {
					Authorization: localStorage.getItem('authToken'),
				},
			});
		} catch (error) {
			console.log(error);
		}

		console.log(result.data);

		if (
			result &&
			result.data &&
			result.data.data &&
			result.data.data.admin &&
			result.data.data.admin == true
		) {
			console.log('USER IS ADMIN');
			navigate('/brickEdition');
		} else {
			console.log('USER IS NOT ADMIN');
			navigate('/login');
			return;
		}
	};

	return (
		<>
			<nav class="bg-white border-gray-200 px-2 sm:px-4 py-2.5 rounded dark:bg-gray-800">
				<div class="container flex flex-wrap justify-between items-center mx-auto">
					<a
						href="https://fr.wikipedia.org/wiki/Brique_(mat%C3%A9riau)"
						class="flex items-center"
					>
						<img
							src={require('./assets/brick0.png')}
							class="mr-3 h-6 sm:h-9"
							alt="brique Logo"
						/>
						<span class="self-center text-xl font-semibold whitespace-nowrap dark:text-white">
							BriqueDatCom
						</span>
					</a>
					<button
						data-collapse-toggle="mobile-menu"
						type="button"
						class="inline-flex items-center p-2 ml-3 text-sm text-gray-500 rounded-lg md:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:text-gray-400 dark:hover:bg-gray-700 dark:focus:ring-gray-600"
						aria-controls="mobile-menu"
						aria-expanded="false"
					>
						<span class="sr-only">Open main menu</span>
						<svg
							class="w-6 h-6"
							fill="currentColor"
							viewBox="0 0 20 20"
							xmlns="http://www.w3.org/2000/svg"
						>
							<path
								fill-rule="evenodd"
								d="M3 5a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 10a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 15a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1z"
								clip-rule="evenodd"
							></path>
						</svg>
						<svg
							class="hidden w-6 h-6"
							fill="currentColor"
							viewBox="0 0 20 20"
							xmlns="http://www.w3.org/2000/svg"
						>
							<path
								fill-rule="evenodd"
								d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
								clip-rule="evenodd"
							></path>
						</svg>
					</button>
					<div
						class="hidden w-full md:block md:w-auto"
						id="mobile-menu"
					>
						<ul class="flex flex-col mt-4 md:flex-row md:space-x-8 md:mt-0 md:text-sm md:font-medium">
							<li>
								<Link
									to="/"
									class="block py-2 pr-4 pl-3 text-gray-700 border-b border-gray-100 hover:bg-gray-50 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-gray-400 md:dark:hover:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700"
								>
									Home
								</Link>
							</li>
							<li>{profileLogin()}</li>
							{registerLogin()}
							<li>
								<Link
									to="/brickCatalog"
									class="block py-2 pr-4 pl-3 text-gray-700 border-b border-gray-100 hover:bg-gray-50 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-gray-400 md:dark:hover:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700"
								>
									Catalog
								</Link>
							</li>
							<li>
								<Link
									to="/basket"
									class="block py-2 pr-4 pl-3 text-gray-700 border-b border-gray-100 hover:bg-gray-50 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-gray-400 md:dark:hover:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700"
								>
									Basket
								</Link>
							</li>
							<li>
								<Link
									to="/orders"
									class="block py-2 pr-4 pl-3 text-gray-700 border-b border-gray-100 hover:bg-gray-50 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-gray-400 md:dark:hover:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700"
								>
									Orders
								</Link>
							</li>
							<li>
								<button
									onClick={handleAdminRoute}
									class="block py-2 pr-4 pl-3 text-gray-700 border-b border-gray-100 hover:bg-gray-50 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-gray-400 md:dark:hover:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700"
								>
									BrickEdition
								</button>
							</li>
						</ul>
					</div>
				</div>
			</nav>
		</>
	);
};

export default Navbar;
