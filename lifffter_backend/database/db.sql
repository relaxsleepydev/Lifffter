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
    updated_at TIMESTAMPTZ DEFAULT now(),
    is_deleted BOOLEAN DEFAULT False,

    CONSTRAINT unique_user_routine UNIQUE (user_id, routine_name),

    CONSTRAINT workout_const
    FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON DELETE CASCADE
);

CREATE TABLE exercises (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL UNIQUE,
    primary_muscle VARCHAR(100) NOT NULL
);

CREATE TABLE routine_exercises (
    exercise_id UUID NOT NULL,
    routine_id UUID NOT NULL,
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_index INT NOT NULL,  
    updated_at TIMESTAMPTZ DEFAULT now(),
    is_deleted BOOLEAN DEFAULT False,  

    CONSTRAINT fk_exercises
    FOREIGN KEY (exercise_id)
    REFERENCES exercises(id)
    ON DELETE CASCADE,

    CONSTRAINT fk_routine
    FOREIGN KEY (routine_id)
    REFERENCES workout_routine(id)
    ON DELETE CASCADE,

    CONSTRAINT unique_exercise_routine UNIQUE (routine_id, exercise_id)

    -- a row cannot exists without referencing a valid,
    -- prexisting id in both the exercises and 
    -- workout_routine tables
);

CREATE TABLE workout_sessions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    routine_id UUID,
    start_time TIMESTAMPTZ DEFAULT now(),
    end_time TIMESTAMPTZ,
    updated_at TIMESTAMPTZ DEFAULT now(),
    is_deleted BOOLEAN DEFAULT False,

    CONSTRAINT fk_workout_sessions
    FOREIGN KEY(user_id)
    REFERENCES users(id)
    ON DELETE CASCADE,

    CONSTRAINT fk_routine_session
    FOREIGN KEY(routine_id)
    REFERENCES workout_routine(id)
    ON DELETE CASCADE
);

CREATE TABLE set_logs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    -- routine_exercises_id UUID NOT NULL, not needed
    session_id UUID NOT NULL,
    exercise_id UUID NOT NULL,
    set_number INT NOT NULL,
    weight DECIMAL NOT NULL,
    reps INT NOT NULL,
    rir INT NOT NULL,
    completed_at TIMESTAMPTZ DEFAULT now(),
    updated_at TIMESTAMPTZ DEFAULT now(),
    is_deleted BOOLEAN DEFAULT False,

    CONSTRAINT fk_logs_sessions
    FOREIGN KEY (session_id)
    REFERENCES workout_sessions(id)
    ON DELETE CASCADE,

    CONSTRAINT fk_logs_exercise
    FOREIGN KEY (exercise_id)
    REFERENCES exercises(id)
    ON DELETE CASCADE
);