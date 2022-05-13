import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

const Basket = () => {
	const [basket, setBasket] = useState('');
	const [basketBrickList, setBasketBrickList] = useState('');

	useEffect(() => {
		try {
			fetchBasket();
		} catch (error) {
			console.log(error);
		}
	}, []);

	const fetchBasket = async () => {
		let result = '';
		try {
			result = await axios.get(
				`/basket/user/${localStorage.getItem('userId')}`,
				{
					// headers: {
					// 	Authorization: localStorage.getItem('authToken'),
					// },
				}
			);
		} catch (error) {
			console.log(error);
		}
		setBasket(result.data.basket.data);
		console.log(result.data.basket.data);

		try {
			result = await axios.get(
				`/bricklist/user/${localStorage.getItem('userId')}`,
				{
					// headers: {
					// 	Authorization: localStorage.getItem('authToken'),
					// },
				}
			);
		} catch (error) {
			console.log(error);
		}
		setBasketBrickList(result.data.brick_lists.data);
		console.log(result.data.brick_lists.data);

		return result;
	};

	const handleEmptyBasket = async () => {
		let result = '';
		try {
			result = await axios.get(
				`/basket/user/${localStorage.getItem('userId')}`,
				{
					// headers: {
					// 	Authorization: localStorage.getItem('authToken'),
					// },
				}
			);
		} catch (error) {
			console.log(error);
		}
		setBasket(result.data.basket.data);
		console.log(result.data.basket.data);

		try {
			result = await axios.put(`/basket/empty/${basket.id}`, {
				// headers: {
				// 	Authorization: localStorage.getItem('authToken'),
				// },
			});
		} catch (error) {
			console.log(error);
		}

		window.location.reload(false);

		return result;
	};

	const handlerOrderBasket = async (event) => {
		let result = '';
		try {
			result = await axios.get(
				`/basket/user/${localStorage.getItem('userId')}`,
				{
					// headers: {
					// 	Authorization: localStorage.getItem('authToken'),
					// },
				}
			);
		} catch (error) {
			console.log(error);
		}
		setBasket(result.data.basket.data);
		console.log(result.data.basket.data);

		try {
			result = await axios.put(`/basket/order/${basket.id}`, {
				// headers: {
				// 	Authorization: localStorage.getItem('authToken'),
				// },
			});
		} catch (error) {
			console.log(error);
		}

		window.location.reload(false);

		return result;
	};

	return (
		<div class="bg-[#AAAAAA]">
			<div className="basket-name">
				<p class="font-medium leading-tight text-5xl mt-0 mb-2 text-black-600">Your basket</p>
			</div>
			<div className="basket-content">
				<ul>
					{basketBrickList &&
						basketBrickList.map((basketBrick) => (
							<li key={basketBrick.brick.id}>
								<Link
									to={`/brickDetails/${basketBrick.brick.id}`}
								>
									{basketBrick.brick.name}
								</Link>
								<p class="font-medium leading-tight text-3xl mt-0 mb-2 text-black-600">Brick number : {basketBrick.quantity}</p>
								<p class="font-medium leading-tight text-3xl mt-0 mb-2 text-black-600">Price : {basketBrick.price}</p>
							</li>
						))}
				</ul>
			</div>
			<div className="basketDetails">
				<div className="basket-image"></div>
				<div className="basket-totalprice">
					<p class="font-medium leading-tight text-3xl mt-0 mb-2 text-black-600">Prix total : {basket.totalPrice} euros.</p>
					<br></br>
				</div>
				<div className="basket-productnb">
					<p class="font-medium leading-tight text-3xl mt-0 mb-2 text-black-600">Nombre de produit : {basket.productNb}</p>
					<br></br>
				</div>
				<div className="orderBasketButton">
					<button onClick={handlerOrderBasket} class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">Order basket</button>
				</div>
				<div className="emptyBasketButtonWrapper">
					<button onClick={handleEmptyBasket} class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">Empty basket</button>
				</div>
			</div>
		</div>
	);
};

export default Basket;
