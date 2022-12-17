import axios from 'axios';

const signin = async ({ email, pwd }) => {
	try {
		const response = await axios.post(
			`${process.env.REACT_APP_BACKENDURL}/signin`,
			{ email, pwd }
		);
		return response.data;
	} catch (error) {
		console.log('unable to signin');
	}
};

export { signin };
