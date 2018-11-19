# Eddystone Scanner

This repository contains an example of using the Android BLE APIs (21+) to scan for an Eddystone-UID beacon. You can find more information about Eddystone at https://github.com/google/eddystone

# Eddystone format

Eddystone is an open beacon format developed by Google and designed with transparency and robustness in mind. Eddystone can be detected by both Android and iOS devices. The Eddystone format builds on lessons learned from working with industry partners in existing deployments, as well as the wider beacon community. Several different types of payload can be included in the frame format, including:

Eddystone-UID: A unique, static ID with a 10-byte Namespace component and a 6-byte Instance component.

Eddystone-URL: A compressed URL that, once parsed and decompressed, is directly usable by the client.

Eddystone-TLM: Beacon status data that is useful for beacon fleet maintenance, and powers Google Proximity Beacon API's diagnostics endpoint. -TLM should be interleaved with an identifying frame such as Eddystone-UID or Eddystone-EID (for which the encrypted eTLM version preserves security).

Eddystone-EID: A time-varying beacon frame that can be resolved to a stable identifier by a linked resolver, such as Proximity Beacon API.

Additionally, Eddystone includes a configuration service and branding materials. To ensure that your deployment fully supports the Google beacon platform, you should set up your beacons with Eddystone-UID or -EID, and optionally add -URL and -TLM.
The Eddystone specification and associated resources are hosted on GitHub.

for more Details : https://developers.google.com/beacons/eddystone
