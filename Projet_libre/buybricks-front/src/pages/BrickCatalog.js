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
				<p className="pageTitle"> Catalog </p>
			</div>
			<div className="catalogFilterWrapper">
				<p className="seqTitle">Filter</p>
				<div className="filterFormWrapper">
					<form onSubmit={handleFilterByName}>
						<label htmlFor="nameFilter">Name</label>
						<input
							type="text"
							id="nameFilter"
							name="nameFilter"
							required
						/>
						<input type="submit" value="Search by name" />
					</form>
				</div>

				<div className="filterFormWrapper">
					<form onSubmit={handleFilterByDim}>
						<label htmlFor="dimFilter">Dimension</label>
						<input
							type="number"
							min="0"
							max="100"
							id="dimFilter"
							name="dimFilter"
							required
						/>
						<input type="submit" value="Search by dimension" />
					</form>
				</div>

				<div className="filterFormWrapper">
					<form onSubmit={handleFilterByPrice}>
						<label htmlFor="priceMinFilter">Minimum price</label>
						<input
							type="number"
							min="0"
							max="100"
							id="priceMinFilter"
							name="priceMinFilter"
							required
						/>

						<label htmlFor="priceMaxFilter">Maximum price</label>
						<input
							type="number"
							min="0"
							max="100"
							id="priceMaxFilter"
							name="priceMaxFilter"
							required
						/>
						<input type="submit" value="Search by price" />
					</form>
				</div>
				<div id="clearFilterButton">
					<button onClick={fetchBricks}>Clear filters</button>
				</div>
			</div>
			<ul>
				{brickList &&
					brickList.map((brick) => (
						<li key={brick.id}>
							<Link to={`/brickDetails/${brick.id}`}>
								{brick.name}
							</Link>
						</li>
					))}
			</ul>
		</div>
	);
};

export default BrickCatalog;
