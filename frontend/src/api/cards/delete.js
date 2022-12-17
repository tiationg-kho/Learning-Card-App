import axios from 'axios';

const deleteCard = async (studentId, cardsetId, cardId) => {
	try {
		const response = await axios.delete(
			`${process.env.REACT_APP_BACKENDURL}/${studentId}/${cardsetId}`,
			{ params: { cardId } }
		);
		return response.data;
	} catch (error) {
		console.log('unable to delete card');
	}
};

export { deleteCard };
