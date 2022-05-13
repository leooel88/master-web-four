import axios from 'axios';
import React from 'react';
import { useNavigate } from 'react-router-dom';

const BrickEdition = () => {
	const navigate = useNavigate();

	// const addCreateBrickButton = () => {
	// 	console.log('local storage : ' + localStorage.getItem('isAdmin'));
	// 	if (
	// 		localStorage.getItem('isAdmin') &&
	// 		localStorage.getItem('isAdmin') == true
	// 	) {
	// 		console.log('User is admin : showing button');
	// 		return (
	// 			<div className="createBrickButtonWrapper">
	// 				<button
	// 					className="createBrickButton"
	// 					onClick={handleCreateBrick}
	// 				>
	// 					Create new Brick
	// 				</button>
	// 			</div>
	// 		);
	// 	} else {
	// 		console.log('User is not admin : not showing button');
	// 		return;
	// 	}
	// };

	const createBrick = async (event) => {
		event.preventDefault();

		const json = {
			brick_name: event.target.brickName.value,
			brick_dim_h: event.target.brickDimH.value,
			brick_dim_l: event.target.brickDimL.value,
			brick_dim_w: event.target.brickDimW.value,
			brick_price: event.target.brickPrice.value,
			brick_quantity: event.target.brickQuantity.value,
			brick_image_url: event.target.brickImageUrl.value,
		};

		let result = '';
		try {
			result = await axios.post('/brick', json, {
				// headers: {
				// 	Authorization: localStorage.getItem('authToken'),
				// },
			});
		} catch (error) {
			console.log(error);
		}

		console.log(result.data);

		navigate('/brickCatalog');
	};

	const handleCreateBrick = async (event) => {
		event.preventDefault();

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

		console.log('Result data : ' + result.data);

		if (
			result &&
			result.data &&
			result.data.data &&
			result.data.data.admin &&
			result.data.data.admin == true
		) {
			console.log('USER IS ADMIN');
			await createBrick(event);
		} else {
			console.log('USER IS NOT ADMIN');
			navigate('/noRight');
		}
		return;
	};

	const deleteBrick = async (event) => {
		event.preventDefault();

		let result = '';
		try {
			result = await axios.delete(
				`/brick/${event.target.brickId.value}`,
				{
					// headers: {
					// 	Authorization: localStorage.getItem('authToken'),
					// },
				}
			);
		} catch (error) {
			console.log(error);
		}

		console.log(result.data);

		navigate('/brickCatalog');
	};

	const handleDeleteBrick = async (event) => {
		event.preventDefault();

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

		console.log('Result data : ' + result.data);

		if (
			result &&
			result.data &&
			result.data.data &&
			result.data.data.admin &&
			result.data.data.admin == true
		) {
			console.log('USER IS ADMIN');
			await deleteBrick(event);
		} else {
			console.log('USER IS NOT ADMIN');
			navigate('/noRight');
		}
		return;
	};

	return (
		<div
			class="flex flex-row justify-center flex-nowrap"
			className="brickEditionWrapper"
		>
			<div class="w-72 mx-20" className="brickCreationFormWrapper flex flex-col grid place-items-center h-screen">
				<div className="brickCreationFormTitleWrapper">
					<p
						class="font-medium leading-tight text-2xl mt-0 mb-2 text-blue-600"
						className="seqTitle"
					>
						Create brick
					</p>
				</div>
				<form
					class="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4"
					onSubmit={handleCreateBrick}
				>
					<div class="mb-4">
						<label
							class="block text-gray-700 text-sm font-bold mb-2"
							htmlFor="brickName"
						>
							Brick name
						</label>
						<input
							class="shadow appearance-none border rounded w-full h-7 py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
							type="text"
							id="brickName"
							name="brickName"
							required
						/>
					</div>

					<div class="mb-4">
						<label
							class="block text-gray-700 text-sm font-bold mb-2"
							htmlFor="brickDimH"
						>
							Brick dimension W
						</label>
						<input
							class="shadow appearance-none border rounded w-full h-7 py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
							type="number"
							id="brickDimH"
							name="brickDimH"
							min="1"
							max="100"
							required
						/>
					</div>

					<div class="mb-4">
						<label
							class="block text-gray-700 text-sm font-bold mb-2"
							htmlFor="brickDimL"
						>
							Brick dimension L
						</label>
						<input
							class="shadow appearance-none border rounded w-full h-7 py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
							type="number"
							id="brickDimL"
							name="brickDimL"
							min="1"
							max="100"
							required
						/>
					</div>

					<div class="mb-4">
						<label
							class="block text-gray-700 text-sm font-bold mb-2"
							htmlFor="brickDimW"
						>
							Brick dimension W
						</label>
						<input
							class="shadow appearance-none border rounded w-full h-7 py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
							type="number"
							id="brickDimW"
							name="brickDimW"
							min="1"
							max="100"
							required
						/>
					</div>

					<div class="mb-4">
						<label
							class="block text-gray-700 text-sm font-bold mb-2"
							htmlFor="brickPrice"
						>
							Brick price
						</label>
						<input
							class="shadow appearance-none border rounded w-full h-7 py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
							type="number"
							id="brickPrice"
							name="brickPrice"
							min="1"
							max="20000"
							required
						/>
					</div>

					<div class="mb-4">
						<label
							class="block text-gray-700 text-sm font-bold mb-2"
							htmlFor="brickQuantity"
						>
							Brick quantity
						</label>
						<input
							class="shadow appearance-none border rounded w-full h-7 py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
							type="number"
							id="brickQuantity"
							name="brickQuantity"
							min="1"
							max="500000"
							required
						/>
					</div>

					<div class="mb-4">
						<label
							class="block text-gray-700 text-sm font-bold mb-2"
							htmlFor="brickImageUrl"
						>
							Brick image URL
						</label>
						<input
							class="shadow appearance-none border rounded w-full h-7 py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
							type="text"
							name="brickImageUrl"
							id="brickImageUrl"
							maxLength="512"
							required
						/>
					</div>

					<div class="flex items-center justify-between">
						<input
							class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
							type="submit"
							value="Create new Brick"
						/>
					</div>
				</form>
			</div>
			<div class="w-72 mx-20" className="brickDeleteFormWrapper flex flex-col grid place-items-center h-screen">
				<div className="brickDeleteFormTitleWrapper">
					<p
						class="font-medium leading-tight text-2xl mt-0 mb-2 text-blue-600"
						className="seqTitle"
					>
						Delete brick
					</p>
				</div>
				<form
					class="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4"
					onSubmit={handleDeleteBrick}
				>
					<div class="mb-4">
						<label
							class="block text-gray-700 text-sm font-bold mb-2"
							htmlFor="brickId"
						>
							Brick id
						</label>
						<input
							class="shadow appearance-none border rounded w-full h-7 py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
							type="number"
							id="brickId"
							name="brickId"
							required
						/>
					</div>
					<div class="flex items-center justify-between">
						<input
							class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
							type="submit"
							value="Delete Brick"
						/>
					</div>
				</form>
			</div>
		</div>
	);
};

export default BrickEdition;
