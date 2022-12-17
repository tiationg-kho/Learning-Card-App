import axios from 'axios';

const create = async (params) => {
	try {
		const response = await axios.post(
			`${process.env.REACT_APP_BACKENDURL}/${params.studentId}/${params.cardsetId}`,
			params.body
		);
		return response.data;
	} catch (error) {
		console.log('unable to create card');
	}
};

export { create };
