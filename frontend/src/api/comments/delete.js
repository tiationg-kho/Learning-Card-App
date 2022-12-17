import axios from 'axios';

const deleteComment = async (studentId, cardsetId, cardId, commentId) => {
	try {
		const response = await axios.delete(
			`${process.env.REACT_APP_BACKENDURL}/${studentId}/${cardsetId}/${cardId}`,
			{ params: { commentId } }
		);
		return response.data;
	} catch (error) {
		console.log('unable to delete comment');
	}
};

export { deleteComment };
