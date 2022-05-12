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
		<div className="basket">
			<div className="basket-name">
				<p className="title product-title">Your basket</p>
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
								<p>Brick number : {basketBrick.quantity}</p>
								<p>Price : {basketBrick.price}</p>
							</li>
						))}
				</ul>
			</div>
			<div className="basketDetails">
				<div className="basket-image"></div>
				<div className="basket-totalprice">
					<p>Prix total : {basket.totalPrice} euros.</p>
					<br></br>
				</div>
				<div className="basket-productnb">
					<p>Nombre de produit : {basket.productNb}</p>
					<br></br>
				</div>
				<div className="orderBasketButton">
					<button onClick={handlerOrderBasket}>Order basket</button>
				</div>
				<div className="emptyBasketButtonWrapper">
					<button onClick={handleEmptyBasket}>Empty basket</button>
				</div>
			</div>
		</div>
	);
};

export default Basket;
