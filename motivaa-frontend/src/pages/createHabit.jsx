import React, { useState } from 'react';
import { createHabit } from '../services/apiClient';

const CreateHabit = () => {
    const [habitData, setHabitData] = useState({
        userUuid: '',
        name: '',
        recurringType: '',
        listOfRecurringDays: [],
        numberOfOccasionsInWeek: 0,
        priority: '',
        color: '',
    });
    const [response, setResponse] = useState(null);
    const [error, setError] = useState(null);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setHabitData({
            ...habitData,
            [name]: value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const result = await createHabit(habitData);
            setResponse(result);
        } catch (error) {
            setError(error.message);
        }
    };

    return (
        <div>
            <h1>Create a New Habit</h1>
            <form onSubmit={handleSubmit}>
                <input name="userUuid" placeholder="User UUID" value={habitData.userUuid} onChange={handleChange} />
                <input name="name" placeholder="Habit Name" value={habitData.name} onChange={handleChange} />
                {/* Add other input fields as necessary */}
                <button type="submit">Create Habit</button>
            </form>
            {response && <div>Habit created: {JSON.stringify(response)}</div>}
            {error && <div>Error: {error}</div>}
        </div>
    );
};

export default CreateHabit;