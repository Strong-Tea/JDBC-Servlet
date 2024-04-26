
CREATE TABLE IF NOT EXISTS device (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    type TEXT NOT NULL,
    manufacturer TEXT NOT NULL,
    model TEXT NOT NULL,
    serial_number TEXT NOT NULL
);

-- One-to-Many
CREATE TABLE IF NOT EXISTS network_interface (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    mac_address TEXT NOT NULL,
    ipv4_address TEXT,
    ipv6_address TEXT,
    gateway_address TEXT,
    device_id INT,
    FOREIGN KEY (device_id) REFERENCES device(id) ON DELETE CASCADE
);

-- One-to-One
CREATE TABLE IF NOT EXISTS network_policy (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    action TEXT,
    FOREIGN KEY (id) REFERENCES network_interface(id) ON DELETE CASCADE
);