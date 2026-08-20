CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ DEFAULT now()
);

CREATE TABLE workout_routine (
    user_id UUID NOT NULL,
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    routine_name VARCHAR(50) NOT NULL,
    target_muscle VARCHAR(50) NOT NULL,
    created_at TIMESTAMPTZ DEFAULT now(),

    CONSTRAINT workout_const
    FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON DELETE CASCADE
);