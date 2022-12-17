import axios from 'axios';

const signup = async ({ email, pwd }) => {
	try {
		const response = await axios.post(
			`${process.env.REACT_APP_BACKENDURL}/signup`,
			{ email, pwd }
		);
		return response.data;
	} catch (error) {
		console.log('unable to signup');
	}
};

export { signup };
