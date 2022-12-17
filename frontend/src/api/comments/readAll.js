import axios from 'axios';

const readAllComment = async (params) => {
	try {
		const response = await axios.get(
			`${process.env.REACT_APP_BACKENDURL}/${params.studentId}/${params.cardsetId}/${params.cardId}`
		);
		return response.data;
	} catch (error) {
		console.log('unable to retrieve comments');
	}
};

export { readAllComment };
