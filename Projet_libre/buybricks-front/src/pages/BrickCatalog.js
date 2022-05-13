import axios from 'axios';
import { Link } from 'react-router-dom';
import React, { useEffect, useState } from 'react';

const BrickCatalog = () => {
	const [brickList, setBrickList] = useState('');

	useEffect(() => {
		try {
			fetchBricks();
		} catch (error) {
			console.log(error);
		}
	}, []);

	const fetchBricks = async () => {
		let result = '';
		try {
			result = await axios.get('/brick', {
				// headers: {
				// 	Authorization: localStorage.getItem('authToken'),
				// },
			});
		} catch (error) {
			console.log(error);
		}
		setBrickList(result.data.bricks.data);
		console.log(result.data.bricks.data);

		return result;
	};

	const handleFilterByName = async (event) => {
		event.preventDefault();
		const searchJson = {
			brick_name: event.target.nameFilter.value,
		};
		let result = '';
		try {
			result = await axios.post('/brick/name/', searchJson, {
				// headers: {
				// 	Authorization: localStorage.getItem('authToken'),
				// },
			});
		} catch (error) {
			console.log(error);
		}
		setBrickList(result.data.bricks.data);
		console.log(result.data.bricks.data);
		return result;
	};

	const handleFilterByDim = async (event) => {
		event.preventDefault();
		const searchJson = {
			brick_dimension: event.target.dimFilter.value,
		};
		let result = '';
		try {
			result = await axios.post('/brick/dimension/', searchJson, {
				// headers: {
				// 	Authorization: localStorage.getItem('authToken'),
				// },
			});
		} catch (error) {
			console.log(error);
		}
		setBrickList(result.data.bricks.data);
		console.log(result.data.bricks.data);
		return result;
	};

	const handleFilterByPrice = async (event) => {
		event.preventDefault();
		const searchJson = {
			brick_price_min: event.target.priceMinFilter.value,
			brick_price_max: event.target.priceMaxFilter.value,
		};
		let result = '';
		try {
			result = await axios.post('/brick/price/', searchJson, {
				// headers: {
				// 	Authorization: localStorage.getItem('authToken'),
				// },
			});
		} catch (error) {
			console.log(error);
		}
		setBrickList(result.data.bricks.data);
		console.log(result.data.bricks.data);
		return result;
	};

	return (
		<div className="flex flex-col items-center brickCatalog">
			<div className="pageTitleWrapper">
				<p className="font-medium leading-tight text-5xl mt-0 mb-2 text-black-600">
					{' '}
					Catalog{' '}
				</p>
			</div>
			<div className="flex flex-row items-center justify-center">
				<p className="font-medium leading-tight text-4xl mt-0 mb-2 text-black-600">
					Filter
				</p>
				<div className="flex flex-col">
					<form
						onSubmit={handleFilterByName}
						className="bg-white shadow-md rounded px-3 pt-3 pb-3 mx-3"
					>
						<div className="mb-4">
							<label
								htmlFor="nameFilter"
								className="block text-gray-700 text-sm font-bold mb-2"
							>
								Name
							</label>
							<input
								className="shadow appearance-none border rounded w-full h-4 py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
								type="text"
								id="nameFilter"
								name="nameFilter"
								required
							/>
						</div>
						<div className="flex items-center justify-between">
							<input
								type="submit"
								value="Search by name"
								className="bg-blue-500 hover:bg-blue-700 text-white font-bold h-7 px-4 rounded focus:outline-none focus:shadow-outline"
							/>
						</div>
					</form>
				</div>

				<div className="filterFormWrapper">
					<form
						onSubmit={handleFilterByDim}
						className="bg-white shadow-md rounded px-3 pt-3 pb-3 mx-3"
					>
						<label htmlFor="dimFilter">Dimension</label>
						<div className="mb-4">
							<input
								className="shadow appearance-none border rounded w-full h-4 py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
								type="number"
								min="0"
								max="100"
								id="dimFilter"
								name="dimFilter"
								required
							/>
						</div>
						<div className="flex items-center justify-between">
							<input
								type="submit"
								value="Search by dimension"
								className="bg-blue-500 hover:bg-blue-700 text-white font-bold h-7 px-4 rounded focus:outline-none focus:shadow-outline"
							/>
						</div>
					</form>
				</div>

				<div className="filterFormWrapper">
					<form
						onSubmit={handleFilterByPrice}
						className="bg-white shadow-md rounded px-3 pt-3 pb-3 mx-3"
					>
						<label htmlFor="priceMinFilter">Minimum price</label>
						<div className="mb-4">
							<input
								className="shadow appearance-none border rounded w-full h-4 py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
								type="number"
								min="0"
								max="100"
								id="priceMinFilter"
								name="priceMinFilter"
								required
							/>
						</div>

						<label htmlFor="priceMaxFilter">Maximum price</label>
						<div className="mb-4">
							<input
								className="shadow appearance-none border rounded w-full h-4 py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
								type="number"
								min="0"
								max="100"
								id="priceMaxFilter"
								name="priceMaxFilter"
								required
							/>
						</div>
						<div className="flex items-center justify-between">
							<input
								type="submit"
								value="Search by price"
								className="bg-blue-500 hover:bg-blue-700 text-white font-bold h-7 px-4 rounded focus:outline-none focus:shadow-outline"
							/>
						</div>
					</form>
				</div>
				<div id="clearFilterButton">
					<button
						onClick={fetchBricks}
						className="bg-blue-500 hover:bg-blue-700 text-white font-bold h-7 px-4 rounded focus:outline-none focus:shadow-outline"
					>
						Clear filters
					</button>
				</div>
			</div>
			<div className="cardListWrapper">
				<ul className="grid grid-cols-4 gap-4 mx-3">
					{brickList &&
						brickList.map((brick) => (
							<li key={brick.id}>
								<Link
									className="block p-6 max-w-sm bg-white rounded-lg border border-gray-200 shadow-md hover:bg-gray-100 dark:bg-gray-800 dark:border-gray-700 dark:hover:bg-gray-700"
									to={`/brickDetails/${brick.id}`}
								>
									<img src="https://www.pngmart.com/files/15/Stack-Brick-Transparent-PNG.png"></img>
									<p className="min-w-fit px-20 justify-self-center text-center text-white">
										{brick.name}
									</p>
								</Link>
							</li>
						))}
				</ul>
			</div>
		</div>
	);
};

export default BrickCatalog;
