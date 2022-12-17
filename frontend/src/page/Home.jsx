import styles from './Home.module.css';
import Sign from '../component/Sign';
import React from 'react';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const Home = () => {
	const [studentId, setStudentId] = useState(localStorage.getItem('studentId'));
	const navi = useNavigate();

	const signoutkHandler = () => {
		localStorage.clear();
		setStudentId(null);
	};

	const cardsetsHandler = () => {
		navi(`/${studentId}`);
	};

	return (
		<>
			<div>welcome to learning card app</div>
			{studentId !== null && (
				<div className={styles.board} onClick={cardsetsHandler}>
					start learning journey
				</div>
			)}
			{studentId === null ? (
				<div>
					<Sign />
				</div>
			) : (
				<div>
					<button onClick={signoutkHandler}>signout</button>
				</div>
			)}
		</>
	);
};

export default Home;
