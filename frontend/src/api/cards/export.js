import axios from 'axios';

const exportCards = async (studentId, cardsetId) => {
	try {
		const response = await axios.get(
			`${process.env.REACT_APP_BACKENDURL}/${studentId}/${cardsetId}/export.csv`
		);

		const blob = new Blob([response.data], { type: 'application/csv' });
		const link = document.createElement('a');
		link.href = window.URL.createObjectURL(blob);
		link.download = 'export.csv';
		link.click();
		return response.data;
	} catch (error) {
		console.log('unable to export cards');
	}
};

export { exportCards };
