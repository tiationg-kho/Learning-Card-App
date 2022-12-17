import React from 'react';
import styles from './Card.module.css';
import { useState } from 'react';

const Card = ({ info }) => {
	const [flag, setFlag] = useState(true);

	return (
		<div className={styles.container} onClick={() => setFlag(!flag)}>
			{flag && <div className={styles.title}>{info.upTitle}</div>}
			{flag && <div className={styles.content}>{info.upContent}</div>}
			{flag && <div className={styles.side}>front side</div>}
			{!flag && <div className={styles.title}>{info.downTitle}</div>}
			{!flag && <div className={styles.content}>{info.downContent}</div>}
			{!flag && <div className={styles.side}>back side</div>}
		</div>
	);
};

export default Card;
