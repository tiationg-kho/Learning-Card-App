import styles from './Cardset.module.css';
import React from 'react';
import { useNavigate } from 'react-router-dom';

const Cardset = ({ info, onRemove: removeHandler }) => {
	const navi = useNavigate();

	const clickHandler = (e) => {
		localStorage.cardsetOwnerId = info.cardset.studentId;
		navi(`/${info.studentId}/${info.cardset.cardsetId}`);
	};

	return (
		<div className={styles.container}>
			<div className={styles.title}>{info.cardset.title}</div>
			<div className={styles.cardsetCategory}>
				cardsetCategory: {info.cardset.cardsetCategory}
			</div>
			<div className={styles.cardsetStatus}>
				cardsetStatus: {info.cardset.cardsetStatus}
			</div>
			<div>
				<button onClick={clickHandler}>see cards in this cardset</button>
			</div>
			{info.studentId === info.cardset.studentId.toString() && (
				<div>
					<button onClick={() => removeHandler(info.cardset.cardsetId)}>
						remove this cardset
					</button>
				</div>
			)}
		</div>
	);
};

export default Cardset;
