import axios from 'axios';
import React from 'react';
import { useParams } from 'react-router-dom';

const BrickDetails = () => {
	const { brickId } = useParams();
	const [brickDetails, setBrickDetails] = React.useState('');

	React.useEffect(() => {
		try {
			fetchBrickDetails();
		} catch (error) {
			console.log(error);
		}
	}, []);

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
				<p>Prix {brickDetails.price} euros.</p>
				<br></br>
			</div>
		</div>
	);
};

export default BrickDetails;
