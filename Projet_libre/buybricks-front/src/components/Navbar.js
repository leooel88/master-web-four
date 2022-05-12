import { Link } from 'react-router-dom';

const Navbar = () => {
	return (
		<>
			<nav class="bg-white border-gray-200 px-2 sm:px-4 py-2.5 rounded dark:bg-gray-800">
				<div class="container flex flex-wrap justify-between items-center mx-auto">
					<a href="https://flowbite.com" class="flex items-center">
						<img src={require('./assets/brick0.png')} class="mr-3 h-6 sm:h-9" alt="bricks Logo" />
						<span class="self-center text-xl font-semibold whitespace-nowrap dark:text-white">BricksDatCom</span>
					</a>
				<div class="hidden w-full md:block md:w-auto" id="mobile-menu">
					<ul class="flex flex-col mt-4 md:flex-row md:space-x-8 md:mt-0 md:text-sm md:font-medium">
						<li>
							<Link to="/" class="block py-2 pr-4 pl-3 text-white bg-blue-700 rounded md:bg-transparent md:text-blue-700 md:p-0 dark:text-white" aria-current="page">Home</Link>
						</li>
						<li>
							<Link to="/profile" class="block py-2 pr-4 pl-3 text-gray-700 border-b border-gray-100 hover:bg-gray-50 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-gray-400 md:dark:hover:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700">Profile</Link>
						</li>
						<li>Address</li>
						<li>Users</li>
						<li>
							<Link to="/register" class="block py-2 pr-4 pl-3 text-gray-700 border-b border-gray-100 hover:bg-gray-50 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-gray-400 md:dark:hover:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700">Register</Link>
						</li>
						<li>
							<Link to="/brickCatalog" class="block py-2 pr-4 pl-3 text-gray-700 border-b border-gray-100 hover:bg-gray-50 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-gray-400 md:dark:hover:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700">Catalog</Link>
						</li>
						<li>
							<Link to="/basket" class="block py-2 pr-4 pl-3 text-gray-700 border-b border-gray-100 hover:bg-gray-50 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-gray-400 md:dark:hover:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700">Basket</Link>
						</li>
					</ul>
				</div>
				</div>
			</nav>
		</>
	);
};

export default Navbar;
