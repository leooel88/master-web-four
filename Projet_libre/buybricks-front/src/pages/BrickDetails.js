import axios from 'axios';
import React from 'react';
import { useParams } from 'react-router-dom';

const BrickDetails = () => {
	const { brickId } = useParams();
	const [brickDetails, setBrickDetails] = React.useState('');
	const [basketId, setBasketId] = React.useState('');
	const [totalPrice, setTotalPrice] = React.useState('');
	const [maximumProductNb, setMaximumProductNb] = React.useState('');

	React.useEffect(() => {
		setTotalPrice(0);
		try {
			fetchBrickDetails();
			fetchBasketDetails();
		} catch (error) {
			console.log(error);
		}
	}, []);

	const fetchBasketDetails = async () => {
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

		setBasketId(result.data.basket.data.id);
		setMaximumProductNb(
			Math.min(
				500 - result.data.basket.data.productNb,
				brickDetails.quantity
			)
		);

		console.log(result.data.basket.data);
		return result;
	};

	const fetchBrickDetails = async () => {
		const result = await axios.get(`/brick/${brickId}`, {
			// headers: {
			// 	Authorization: localStorage.getItem('authToken'),
			// },
		});
		console.log(result);
		setBrickDetails(result.data.brick.data);
		return result;
	};

	const handleChangeBrickNb = (event) => {
		event.preventDefault();
		setTotalPrice(event.target.value * brickDetails.price);
	};

	const fetchAddBrick = async (addBrickJson) => {
		let result = await axios.get(
			`/basket/user/${localStorage.getItem('userId')}`,
			{
				// headers: {
				// 	Authorization: localStorage.getItem('authToken'),
				// },
			}
		);
		console.log(result);
		setBasketId(result.data.basket.data.id);

		result = await axios.put(`/basket/${basketId}`, addBrickJson, {
			// headers: {
			// 	Authorization: localStorage.getItem('authToken'),
			// },
		});
		console.log(result);
	};

	const handleAddBrick = async (event) => {
		event.preventDefault();
		const addBrickJson = {
			brick_id: brickId,
			brick_quantity: event.target.brickNb.value,
		};
		await fetchAddBrick(addBrickJson);
	};

	return (
		<div className="brickDetails">
			<div className="brickDetails-name">
				<p className="title product-title">{brickDetails.name}</p>
			</div>
			<div className="brickDetails-image"></div>
			<div className="brickDetails-price">
				<p>Prix {brickDetails.price} euros.</p>
				<br></br>
			</div>
			<div className="brickDetails-dim">
				<p>Hauteur {brickDetails.dimH} cm.</p>
				<br></br>
				<p>Largeur {brickDetails.dimL} cm.</p>
				<br></br>
				<p>Profondeur {brickDetails.dimW} cm.</p>
				<br></br>
			</div>
			<div className="brickDetails-quantity">
				<p>Quantité disponible {brickDetails.quantity}</p>
				<br></br>
			</div>
			<form onSubmit={handleAddBrick} onChange={handleChangeBrickNb}>
				<label htmlFor="brickNb">Brick number</label>
				<input
					type="number"
					id="brickNb"
					name="brickNb"
					min="0"
					max={maximumProductNb}
					placeholder="Number of brick"
					required
				/>

				<p id="totalPrice" name="totalPrice">
					Total price : {totalPrice} euros.
				</p>

				<input type="submit" value="Add to basket" />
			</form>
		</div>
	);
};

export default BrickDetails;
