
export async function createHabit(habitData) {
    const response = await fetch('http://localhost:8093/habit-apis/habits', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(habitData),
    });

    if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Failed to create habit');
    }

    return response.json();
}

export async function searchHabits(userUuid) {
    const response = await fetch(`http://localhost:8093/habit-apis/habits?userUuid=${userUuid}`);

    if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Failed to fetch habits');
    }

    return response.json();
}