import axios from 'axios';
import React from 'react';

const BrickCatalog = () => {
	const [brickList, setBrickList] = React.useState('');

	React.useEffect(() => {
		try {
			fetchBricks();
		} catch (error) {
			console.log(error);
		}
	}, []);

	const fetchBricks = async () => {
		const result = await axios.get('/brick', {
			// headers: {
			// 	Authorization: localStorage.getItem('authToken'),
			// },
		});
		setBrickList(result.data.bricks);
		return result;
	};

	return <div></div>;
	// <ul>
	//       {posts.map(({ id, title }) => (
	//         <li key={id}>
	//           <Link to={`blog/${id}`}>{title}</Link>
	//         </li>
	//       ))}
	//     </ul>
};

export default BrickCatalog;
