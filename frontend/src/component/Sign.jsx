import styles from './Sign.module.css';
import { signin } from '../api/students/signin';
import { signup } from '../api/students/signup';
import React from 'react';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const Sign = () => {
	const [email, setEmail] = useState('');
	const [pwd, setPwd] = useState('');
	const [error, setError] = useState('');
	const navi = useNavigate();
	let data = null;
	const emailHandler = (e) => {
		setError('');
		setEmail(e.target.value);
	};
	const pwdHandler = (e) => {
		setError('');
		setPwd(e.target.value);
	};
	const clickHandler = async (flag) => {
		if (!/\S+@\S+\.\S+/.test(email)) {
			setError('email must be a valid email address');
			return;
		}
		if (pwd.length < 5) {
			setError('length of password must larger than 5');
			return;
		}
		if (flag) {
			data = await signup({ email, pwd });
			if (data === null) {
				setError('unable to register, please try again');
				return;
			}
		} else {
			data = await signin({ email, pwd });
			if (data === null) {
				setError('unexisted email or wrong password');
				return;
			}
		}
		localStorage.studentId = data.studentId;
		localStorage.studentEmail = email;
		navi(`/${data.studentId}`);
	};
	return (
		<>
			<div className={styles.title}>please signup / signin first</div>
			<div className={styles.input}>
				<input type='email' value={email} onChange={emailHandler} />
			</div>
			<div className={styles.input}>
				<input type='password' value={pwd} onChange={pwdHandler} />
			</div>
			<div className={styles.btn}>
				<button onClick={() => clickHandler(true)}>signup</button>
				<button onClick={() => clickHandler(false)}>signin</button>
			</div>
			{error.length > 0 && <div className={styles.error}>{error}</div>}
		</>
	);
};

export default Sign;
