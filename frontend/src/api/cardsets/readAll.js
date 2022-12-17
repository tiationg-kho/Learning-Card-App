import axios from 'axios';

const readAll = async (params) => {
	try {
		const response = await axios.get(
			`${process.env.REACT_APP_BACKENDURL}/${params.studentId}`,
			{ params }
		);
		return response.data;
	} catch (error) {
		console.log('unable to retrieve cardsets');
	}
};

export { readAll };
