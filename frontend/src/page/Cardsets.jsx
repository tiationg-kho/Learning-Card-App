import styles from './Cardsets.module.css';
import React from 'react';
import { readAll } from '../api/cardsets/readAll';
import { useState, useEffect } from 'react';
import Cardset from '../component/Cardset';
import { deleteCardset } from '../api/cardsets/delete';
import { create } from '../api/cardsets/create';

const Cardsets = () => {
	const [params, setParams] = useState({
		studentId: localStorage.getItem('studentId'),
		cardsetCategory: null,
		cardsetStatus: null,
		keyword: null,
		limit: 6,
		offset: 0,
	});
	const [cardsets, setCardsets] = useState([]);
	const [keyword, setKeyword] = useState('');
	const [cardsetCategory, setCardsetCategory] = useState(null);
	const [cardsetStatus, setCardsetStatus] = useState(null);
	const [title, setTitle] = useState('');
	const [addCardsetCategory, setAddCardsetCategory] = useState('SQL');
	const [addCardsetStatus, setAddCardsetStatus] = useState('PRIVATE');
	const [rest, setRest] = useState(true);
	let data = null;

	useEffect(() => {
		readAllHandler();
	}, []);

	const readAllHandler = async (flag) => {
		data = await readAll(params);
		if (flag === 'load') {
			setCardsets((prev) => prev.concat(data.objects));
		} else {
			setCardsets(data.objects);
		}
		if (data.total > data.limit + data.offset) {
			setRest(true);
			setParams({ ...params, offset: data.limit + data.offset });
		} else {
			setRest(false);
		}
	};

	const keywordHandler = (e) => {
		setKeyword(e.target.value);
		params.offset = 0;
		if (e.target.value.length !== 0) {
			params.keyword = e.target.value;
		} else {
			params.keyword = null;
		}
		readAllHandler();
	};

	const cardsetCategoryHandler = (e) => {
		setCardsetCategory(e.target.value);
		params.offset = 0;
		if (e.target.value !== 'ALL') {
			params.cardsetCategory = e.target.value;
		} else {
			params.cardsetCategory = null;
		}
		readAllHandler();
	};

	const cardsetStatusHandler = (e) => {
		setCardsetStatus(e.target.value);
		params.offset = 0;
		if (e.target.value !== 'NO') {
			params.cardsetStatus = 'PUBLIC';
		} else {
			params.cardsetStatus = null;
		}
		readAllHandler();
	};

	const removeHandler = async (cardsetId) => {
		const data = await deleteCardset(params.studentId, cardsetId);
		params.offset -= 1;
		const newCardsets = cardsets.filter((item) => item.cardsetId !== cardsetId);
		setCardsets(newCardsets);
	};

	const addHandler = async () => {
		const addParams = {
			title,
			cardsetCategory: addCardsetCategory,
			cardsetStatus: addCardsetStatus,
			studentId: params.studentId,
		};
		const addData = await create(addParams);
		params.offset = 0;
		readAllHandler();
	};

	return (
		<>
			<div>hi, {localStorage.getItem('studentEmail')}</div>
			{cardsets.length === 0 && <div>no existed cardset, please add one</div>}
			<div className={styles.inputContainer}>
				<div className={styles.searchInput}>
					<div>search cardsets</div>
					<div>
						search by keyword:{' '}
						<input type='text' value={keyword} onChange={keywordHandler} />
					</div>
					<div>
						seaarch by category:
						<select
							name='cardsetCategory'
							id='cardsetCategory'
							onChange={cardsetCategoryHandler}
						>
							<option>ALL</option>
							<option>SQL</option>
							<option>ALGORITHM</option>
							<option>DATA_STRUCTURE</option>
						</select>
					</div>
					<div>
						search by public resources:
						<select
							name='cardsetStatus'
							id='cardsetStatus'
							onChange={cardsetStatusHandler}
						>
							<option>NO</option>
							<option>YES</option>
						</select>
					</div>
				</div>
				<div className={styles.addInput}>
					<div>
						title:{' '}
						<input
							type='text'
							value={title}
							onChange={(e) => setTitle(e.target.value)}
						/>
					</div>
					<div>
						category:
						<select
							name='addCardsetCategory'
							id='addCardsetCategory'
							onChange={(e) => setAddCardsetCategory(e.target.value)}
						>
							<option>SQL</option>
							<option>ALGORITHM</option>
							<option>DATA_STRUCTURE</option>
						</select>
					</div>
					<div>
						private or public:
						<select
							name='addCardsetStatus'
							id='addCardsetStatus'
							onChange={(e) => setAddCardsetStatus(e.target.value)}
						>
							<option>PRIVATE</option>
							<option>PUBLIC</option>
						</select>
					</div>
					<div>
						<button disabled={title === ''} onClick={addHandler}>
							add cardset
						</button>
					</div>
				</div>
			</div>
			{cardsets.map((cardset) => {
				const key = cardset.cardsetId.toString().concat(cardset.title);
				return (
					<Cardset
						onRemove={removeHandler}
						info={{ cardset, studentId: params.studentId }}
						key={key}
					/>
				);
			})}
			{rest && (
				<div>
					<button onClick={() => readAllHandler('load')}>load more</button>
				</div>
			)}
		</>
	);
};

export default Cardsets;
