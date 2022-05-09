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

	return (
		<div className="brickCatalog">
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
			<Link to={`/basket/${localStorage.getItem('userId')}`}>Basket</Link>
		</div>
	);
};

export default BrickCatalog;
