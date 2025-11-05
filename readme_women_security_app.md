# Women Security App

> **A compassionate, privacy-first safety application to help women quickly request help, share live location, and alert trusted contacts and responders.**

---

## Table of Contents
- [Overview](#overview)
- [Key Features](#key-features)
- [Architecture & Tech Stack](#architecture--tech-stack)
- [Installation (Developer)](#installation-developer)
- [Configuration](#configuration)
- [Running Locally](#running-locally)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Security & Privacy](#security--privacy)
- [Testing](#testing)
- [Accessibility](#accessibility)
- [Deployment](#deployment)
- [Contributing](#contributing)
- [License](#license)
- [Contact & Support](#contact--support)

---

## Overview
Women Security App is a cross-platform (mobile-first) application built to provide immediate safety tools to users in distress. The app focuses on fast, reliable, and privacy-respecting features such as emergency SOS alerts, live location sharing, automatic call recording (optional, where legal), quick-access safety contacts, and one-tap recording of environment.

The goal is to minimize friction during emergencies while maximizing reliability and respecting user consent and data protection regulations.

## Key Features
- One-tap **SOS** button that:
  - Sends alert SMS/WhatsApp (configurable) to selected emergency contacts.
  - Calls a pre-configured emergency number (user choice).
  - Streams or periodically shares live location (latitude, longitude) to trusted contacts and to a monitoring endpoint.
- **Shake / Long-press / Voice activation** to trigger SOS when tapping is not possible.
- **Live audio/video streaming** (optional) to trusted contacts or a central responder service.
- **Location tracking** with configurable frequency and geo-fencing alerts.
- **Trusted contact management** with custom messages and escalation flow.
- **Incident history** on device (encrypted) with export option (user-controlled).
- **False alarm reduction** — cancel window and multi-step verification for non-critical triggers.
- **In-app resources** (hotlines, safe routes, tips) and an educational onboarding flow.

## Architecture & Tech Stack
- Mobile: React Native / Flutter (choose one) for cross-platform mobile app.
- Backend: Node.js + Express or Spring Boot (Java) — REST + WebSocket for real-time features.
- Database: MongoDB / PostgreSQL for user metadata; Redis for ephemeral session/state.
- Real-time: WebSocket or WebRTC for live audio/video and location streaming.
- Push notifications: Firebase Cloud Messaging (FCM) / APNs.
- SMS/Voice: Twilio or local telecom gateway (region-specific).
- Auth: JWT + OAuth2 for social login options (optional).
- Hosting: Docker containers on AWS / GCP / Azure or serverless functions for scale.

## Installation (Developer)
> These are generic steps — adapt to the chosen stack.

1. Clone the repo:
```bash
git clone https://github.com/<org>/women-security-app.git
cd women-security-app
```

2. Install backend dependencies:
```bash
cd backend
npm install   # or mvn install for Spring Boot
```

3. Install mobile dependencies:
```bash
cd ../mobile
npm install
# or flutter pub get
```

## Configuration
Create a `.env` file in `backend/` with the following (example):
```
PORT=8080
MONGO_URI=mongodb://localhost:27017/women_security
JWT_SECRET=replace_with_secure_random
FCM_SERVER_KEY=your_fcm_server_key
TWILIO_SID=your_twilio_sid
TWILIO_AUTH_TOKEN=your_twilio_token
TWILIO_PHONE=+1234567890
EMERGENCY_NUMBER=112
```

Mobile config (example `config.js` / `env`):
- Default emergency contact(s)
- Toggle for auto-recording
- Backend API base URL

**Important:** Never commit secrets to source control. Use environment-specific secret managers for production (AWS Secrets Manager, GCP Secret Manager, Vault).

## Running Locally
### Backend
```bash
cd backend
npm run dev   # or ./mvnw spring-boot:run
```

### Mobile (React Native)
```bash
cd mobile
npx react-native run-android
npx react-native run-ios
```

For Flutter:
```bash
cd mobile
flutter run
```

## Usage
- First-time users are walked through onboarding to configure emergency contacts, permissions (location, microphone, camera), and preferred trigger method (tap, shake, voice).
- In an emergency: press the SOS button (or use configured trigger). The app starts the escalation flow: local alert → notify contacts → call emergency number → stream location/audio as configured.
- Users can view and manage incident history and export logs if required.

## API Endpoints (example)
```
POST /api/auth/register        # register user
POST /api/auth/login           # login (returns JWT)
GET  /api/profile              # fetch user profile (auth)
PUT  /api/profile              # update profile
POST /api/sos                  # trigger SOS (auth)
POST /api/contacts             # manage trusted contacts
GET  /api/incidents            # list incidents (auth)
POST /api/incidents/:id/cancel # cancel incident
WS   /ws/live/:incidentId      # websocket for live location/audio
```

## Security & Privacy
This project treats safety and privacy as first-class citizens.
- **Permissions:** Request only essential permissions at runtime and explain clearly why.
- **Data minimization:** Store the minimum necessary data. Use short retention windows for sensitive data.
- **Encryption:** All data in transit must use TLS (HTTPS). Encrypt sensitive fields at rest (e.g., PII, location history) using a server-side encryption key.
- **Access control:** Role-based access control for operator/first-responder dashboards. JWTs should be short-lived with refresh tokens.
- **Consent & Legal:** Provide explicit consent flows for audio/video recording and explain local legal considerations (recording consent differs by jurisdiction).
- **Anonymization:** Where possible, anonymize logs used for analytics.

## Testing
- Unit tests: `npm run test` (backend & mobile).
- Integration tests: scripts or Postman collection for API endpoints.
- E2E: Detox (React Native) / Flutter integration tests.
- Security testing: static code analysis, dependency vulnerability scanning, and pen tests before production release.

## Accessibility
- Large tap targets and high-contrast UI for low-vision users.
- VoiceOver / TalkBack support and clear ARIA / accessibility labels.
- Haptic feedback and vibration patterns for confirmations when available.

## Deployment
- Use CI/CD (GitHub Actions, GitLab CI) to run tests and build artifacts.
- Containerize services with Docker; use Kubernetes or managed container services for scale.
- Monitor with logging & alerting (Prometheus + Grafana, ELK stack).

## Contributing
Contributions are welcome! Please:
1. Fork the repo.
2. Create a feature branch: `git checkout -b feat/your-feature`.
3. Open a PR with a clear description and tests where applicable.
4. Follow semantic commit messages and our code style guide.

## License
This project is released under the MIT License. See `LICENSE` for details.

## Contact & Support
If you find a security issue, please contact the maintainers immediately at `security@example.com` and avoid opening a public issue for sensitive problems. For general support, raise a GitHub issue or contact `support@example.com`.

---

**Notes & Next steps**
- Consider region-specific partnerships with local emergency services and NGOs.
- Build an offline-mode with SMS and USSD fallbacks for regions with poor internet connectivity.
- Regularly review legal requirements for audio/video recording and emergency dispatch integrations.

*Thank you for building safer communities.*

