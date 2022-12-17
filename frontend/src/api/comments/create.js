import axios from 'axios';

const createComment = async (params) => {
	try {
		const response = await axios.post(
			`${process.env.REACT_APP_BACKENDURL}/${params.studentId}/${params.cardsetId}/${params.cardId}`,
			params.body
		);
		return response.data;
	} catch (error) {
		console.log('unable to create comment');
	}
};

export { createComment };
