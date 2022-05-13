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
		<div className="brickCatalog">
			<div className="pageTitleWrapper">
				<p class="font-medium leading-tight text-5xl mt-0 mb-2 text-black-600"> Catalog </p>
			</div>
			<div class="flex flex-col">
				<p class="font-medium leading-tight text-4xl mt-0 mb-2 text-black-600">Filter</p>
				<div class="flex flex-col">
					<form onSubmit={handleFilterByName} class="bg-white shadow-md rounded px-8 pt-6 pb-8">
					<div class="mb-4">
						<label htmlFor="nameFilter" class="block text-gray-700 text-sm font-bold mb-2">Name</label>
						<input
							class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
							type="text"
							id="nameFilter"
							name="nameFilter"
							required
						/>
						</div>
					<div class="flex items-center justify-between">
						<input type="submit" value="Search by name" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"/>
					</div>
					</form>
				</div>

				<div className="filterFormWrapper">
					<form onSubmit={handleFilterByDim} class="bg-white shadow-md rounded px-8 pt-6 pb-8">
						<label htmlFor="dimFilter">Dimension</label>
						<div class="mb-4">
						<input
							class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
							type="number"
							min="0"
							max="100"
							id="dimFilter"
							name="dimFilter"
							required
						/>
						</div>
						<div class="flex items-center justify-between">
						<input type="submit" value="Search by dimension" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"/>
						</div>
					</form>
				</div>

				<div className="filterFormWrapper">
					<form onSubmit={handleFilterByPrice} class="bg-white shadow-md rounded px-8 pt-6 pb-8">
						<label htmlFor="priceMinFilter">Minimum price</label>
						<div class="mb-4">
						<input
							class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
							type="number"
							min="0"
							max="100"
							id="priceMinFilter"
							name="priceMinFilter"
							required
						/>
						</div>

						<label htmlFor="priceMaxFilter">Maximum price</label>
						<div class="mb-4">
						<input
							class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
							type="number"
							min="0"
							max="100"
							id="priceMaxFilter"
							name="priceMaxFilter"
							required
						/>
						</div>
						<div class="flex items-center justify-between">
						<input type="submit" value="Search by price" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"/>
						</div>
					</form>
				</div>
				<div id="clearFilterButton" >
					<button onClick={fetchBricks}>Clear filters</button>
				</div>
			</div>
			<div className="cardListWrapper">
				<ul>
					{brickList &&
						brickList.map((brick) => (
							<div className="brickCardWrapper">
								<li key={brick.id}>
									<Link to={`/brickDetails/${brick.id}`}>
										{brick.name}
									</Link>
								</li>
							</div>
						))}
				</ul>
			</div>
		</div>
	);
};

export default BrickCatalog;
