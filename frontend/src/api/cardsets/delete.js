import axios from 'axios';

const deleteCardset = async (studentId, cardsetId) => {
	try {
		const response = await axios.delete(
			`${process.env.REACT_APP_BACKENDURL}/${studentId}`,
			{ params: { cardsetId } }
		);
		return response.data;
	} catch (error) {
		console.log('unable to delete cardset');
	}
};

export { deleteCardset };
