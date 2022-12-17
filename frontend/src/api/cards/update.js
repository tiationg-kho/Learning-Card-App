import axios from 'axios';

const update = async (params) => {
	try {
		const response = await axios.put(
			`${process.env.REACT_APP_BACKENDURL}/${params.studentId}/${params.cardsetId}`,
			params.body,
			{ params: { cardId: params.cardId } }
		);
		return response.data;
	} catch (error) {
		console.log('unable to update card');
	}
};

export { update };
