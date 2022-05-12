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
							<div
								class="flex flex-col items-center bg-white rounded-lg border shadow-md md:flex-row md:max-w-xl hover:bg-gray-100 dark:border-gray-700 dark:bg-gray-800 dark:hover:bg-gray-700"
								className="orderWrapper"
							>
								<div class="flex flex-col justify-between p-4 leading-normal">
									<p class="mb-3 font-normal text-gray-700 dark:text-gray-400">
										Order id : {order.id}
									</p>
									<br></br>
									<p class="mb-3 font-normal text-gray-700 dark:text-gray-400">
										Order total price : {order.totalPrice}
									</p>
									<br></br>
									<p class="mb-3 font-normal text-gray-700 dark:text-gray-400">
										Order number of products :{' '}
										{order.productNb}
									</p>
								</div>

								<br></br>
							</div>
						</li>
					))}
			</ul>
		</div>
	);
};

export default Orders;
