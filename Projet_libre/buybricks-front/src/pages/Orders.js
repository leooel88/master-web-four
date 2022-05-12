import axios from 'axios';
import { Link } from 'react-router-dom';
import React, { useEffect, useState } from 'react';

const Orders = () => {
	const [orderList, setOrderList] = useState('');

	useEffect(() => {
		try {
			fetchOrders();
		} catch (error) {
			console.log(error);
		}
	}, []);

	const fetchOrders = async () => {
		let result = '';
		try {
			result = await axios.get(`/order/user/${localStorage.userId}`, {
				// headers: {
				// 	Authorization: localStorage.getItem('authToken'),
				// },
			});
		} catch (error) {
			console.log(error);
		}
		setOrderList(result.data.orders.data);
		console.log(result.data.orders.data);
		return result;
	};

	return (
		<div className="ordersList">
			<ul>
				{orderList &&
					orderList.map((order) => (
						<li key={order.id}>
							<div className="orderWrapper">
								<p>Order id : {order.id}</p>
								<br></br>
								<p>Order total price : {order.totalPrice}</p>
								<br></br>
								<p>
									Order number of products : {order.productNb}
								</p>
								<br></br>
							</div>
						</li>
					))}
			</ul>
		</div>
	);
};

export default Orders;
